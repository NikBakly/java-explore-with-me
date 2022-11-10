package ru.yandex.main.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.main.GlobalVariable;
import ru.yandex.main.category.Category;
import ru.yandex.main.category.CategoryRepository;
import ru.yandex.main.event.*;
import ru.yandex.main.exception.BadRequestException;
import ru.yandex.main.exception.NotFoundException;
import ru.yandex.main.statistic.Client;
import ru.yandex.main.statistic.ViewStats;
import ru.yandex.main.user.request.RequestService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private final RequestService requestService;
    private final Client client;

    private static final Integer TWO_HOUR = 2;

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> findUserEventsById(Long userId,
                                                  Integer from,
                                                  Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<Event> events = eventRepository.findAllByInitiatorId(userId, pageable).getContent();
        List<Long> eventIds = new ArrayList<>();
        events.forEach(event -> eventIds.add(event.getId()));
        List<EventShortDto> result = EventMapper.toEventsShortDto(events, getHistFromViewStats(eventIds), getConfirmedRequest(eventIds));
        log.info("User's events were found successfully");
        return result;
    }

    @Override
    @Transactional
    public EventFullDto updateUserEventById(Long userId, UpdateEventRequest updateEventRequest) {
        Category newCategory = findAndCheckCategoryById(updateEventRequest.getCategory());
        // дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
        if (updateEventRequest.getEventDate() != null) {
            checkTime(updateEventRequest.getEventDate());
        }
        Event foundEvent = findAndCheckEventByIdAndUserId(updateEventRequest.getEventId(), userId);
        // изменить можно только отмененные события или события в состоянии ожидания модерации
        if (foundEvent.getState().equals(State.CONFIRMED) || foundEvent.getState().equals(State.PUBLISHED)) {
            log.warn("The update event should be canceled or pending");
            throw new BadRequestException("The update event should be canceled or is pending");
        }
        // Обновления события
        if (updateEventRequest.getAnnotation() != null) {
            foundEvent.setAnnotation(updateEventRequest.getAnnotation());
        }
        if (updateEventRequest.getCategory() != null) {
            foundEvent.setCategory(newCategory);
        }
        if (updateEventRequest.getDescription() != null) {
            foundEvent.setDescription(updateEventRequest.getDescription());
        }
        if (updateEventRequest.getEventDate() != null) {
            foundEvent.setEventDate(LocalDateTime.parse(updateEventRequest.getEventDate(), GlobalVariable.TIME_FORMATTER));
        }
        if (updateEventRequest.getPaid() != null) {
            foundEvent.setPaid(updateEventRequest.getPaid());
        }
        if (updateEventRequest.getParticipantLimit() != null) {
            foundEvent.setParticipantLimit(updateEventRequest.getParticipantLimit());
        }
        if (updateEventRequest.getTitle() != null) {
            foundEvent.setTitle(updateEventRequest.getTitle());
        }
        // если редактируется отменённое событие, то оно автоматически переходит в состояние ожидания модерации
        if (foundEvent.getState().equals(State.CANCELED)) {
            foundEvent.setState(State.PENDING);
        }
        eventRepository.save(foundEvent);
        log.info("Event with id={} was updated successfully", foundEvent.getId());
        return EventMapper.toEventFullDto(foundEvent, getHistFromViewStats(foundEvent.getId()), getConfirmedRequests(foundEvent.getId()));
    }

    @Override
    @Transactional
    public EventFullDto createUserEvent(Long userId, NewEventDto newEventDto) {
        User foundUser = findAndCheckUserById(userId);
        Category foundCategory = findAndCheckCategoryById(newEventDto.getCategory());
        // дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
        checkTime(newEventDto.getEventDate());
        Event event = EventMapper.toEvent(newEventDto, foundCategory, foundUser);
        eventRepository.save(event);
        log.info("Event with id={} was created successfully", event.getId());
        return EventMapper.toEventFullDto(event, 0L, 0L);
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto findUserEventByUserIdAndByEventId(Long userId, Long eventId) {
        Event foundEvent = findAndCheckEventByIdAndUserId(eventId, userId);
        log.info("User's event with id={} and user with id={} were found successfully", eventId, userId);
        return EventMapper.toEventFullDto(foundEvent, getHistFromViewStats(eventId), getConfirmedRequests(eventId));
    }

    @Override
    @Transactional
    public EventFullDto eventCancellation(Long userId, Long eventId) {
        Event foundEvent = findAndCheckEventByIdAndUserId(eventId, userId);
        if (!foundEvent.getState().equals(State.PENDING)) {
            log.warn("The event should be pending");
            throw new BadRequestException("The event should be pending");
        }
        foundEvent.setState(State.CANCELED);
        eventRepository.save(foundEvent);
        return EventMapper.toEventFullDto(foundEvent, getHistFromViewStats(eventId), getConfirmedRequests(eventId));
    }

    private Event findAndCheckEventByIdAndUserId(Long eventId, Long userId) {
        checkEventById(eventId);
        findAndCheckUserById(userId);
        Optional<Event> foundEvent = eventRepository.findByIdAndInitiatorId(eventId, userId);
        if (foundEvent.isEmpty()) {
            log.warn("Event with id={} and initiator with id={} was not found", eventId, userId);
            throw new NotFoundException("Event with id="
                    + eventId +
                    " and initiator with id=" + userId + " was not found");
        }
        return foundEvent.get();
    }

    private void checkEventById(Long eventId) {
        Optional<Event> foundEvent = eventRepository.findById(eventId);
        if (foundEvent.isEmpty()) {
            log.warn("Event with id={} was not found.", eventId);
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
    }

    private User findAndCheckUserById(Long userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            log.warn("User with id={} was not found.", userId);
            throw new NotFoundException("User with id=" + userId + " was not found.");
        }
        return foundUser.get();
    }

    private Category findAndCheckCategoryById(Long categoryId) {
        Optional<Category> foundCategory = categoryRepository.findById(categoryId);
        if (foundCategory.isEmpty()) {
            log.warn("Category with id={} was not found.", categoryId);
            throw new NotFoundException("Category with id=" + categoryId + " was not found.");
        }
        return foundCategory.get();
    }

    private static void checkTime(String dateTimeString) {
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, GlobalVariable.TIME_FORMATTER);
        if (!dateTime.isAfter(LocalDateTime.now().plusHours(TWO_HOUR))) {
            log.warn("The time is incorrect when interacting with the event");
            throw new BadRequestException("TThe time is incorrect when interacting with the event");
        }
    }

    // возврат количества просмотров у события
    private Long getHistFromViewStats(Long eventId) {
        String uri = "/event/" + eventId;
        Optional<ViewStats> viewStats = client.findByUrl(
                        LocalDateTime.now().minusYears(GlobalVariable.FIVE_YEARS).format(GlobalVariable.TIME_FORMATTER),
                        LocalDateTime.now().plusYears(GlobalVariable.FIVE_YEARS).format(GlobalVariable.TIME_FORMATTER),
                        uri,
                        false)
                .stream().findFirst();
        if (viewStats.isEmpty()) {
            log.info("Statistics for event with id={} were not found so 0 views are returned", eventId);
            return 0L;
        }
        return viewStats.get().getHits();
    }

    private List<Long> getHistFromViewStats(List<Long> eventIds) {
        List<Long> hits = new ArrayList<>();
        StringBuilder uri = new StringBuilder();
        for (int i = 0; i < eventIds.size(); i++) {
            if (i == eventIds.size() - 1) {
                uri.append("/events/").append(eventIds.get(i));
            } else {
                uri.append("/events/").append(eventIds.get(i)).append(",");
            }
        }
        List<ViewStats> viewStats = client.findByUrl(
                LocalDateTime.now().minusYears(GlobalVariable.FIVE_YEARS).format(GlobalVariable.TIME_FORMATTER),
                LocalDateTime.now().plusYears(GlobalVariable.FIVE_YEARS).format(GlobalVariable.TIME_FORMATTER),
                uri.toString(),
                false);
        if (viewStats.isEmpty()) {
            // заполняем пустые места нулями
            eventIds.forEach(aLong -> hits.add(0L));
        } else {
            // заполняем данными
            viewStats.forEach(viewStats1 -> hits.add(viewStats1.getHits()));
        }
        return hits;
    }

    // возврат количество подтвержденных заявок по идентификатору события
    private Long getConfirmedRequests(Long eventId) {
        return requestService.getNumberOfConfirmedRequests(eventId);
    }

    private List<Long> getConfirmedRequest(List<Long> eventIds) {
        return requestService.getNumberOfConfirmedRequests(eventIds);
    }

}
