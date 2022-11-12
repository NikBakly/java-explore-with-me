package ru.yandex.main.admin.event;

import com.querydsl.core.types.dsl.BooleanExpression;
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
import ru.yandex.main.user.comment.Comment;
import ru.yandex.main.user.comment.CommentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;

    private final EventServiceImpl eventService;

    private static final Integer ONE_HOUR = 1;

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> findEvents(EventFilterAdmin eventFilterAdmin) {
        Pageable pageable = PageRequest.of(eventFilterAdmin.getFrom(), eventFilterAdmin.getSize());
        List<Event> foundEvent = eventRepository.findAll(formatExpression(eventFilterAdmin), pageable).getContent();
        List<Long> eventIds = new ArrayList<>();
        foundEvent.forEach(event -> eventIds.add(event.getId()));
        List<EventFullDto> result = EventMapper
                .toEventsFullDto(
                        foundEvent,
                        eventService.getHistFromViewStats(eventIds),
                        eventService.getConfirmedRequest(eventIds));
        log.info("Events were found successfully");
        return result;
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long eventId, AdminUpdateEventRequest updateEventRequest) {
        Event foundEvent = findAndCheckEventById(eventId);
        if (updateEventRequest.getAnnotation() != null) {
            foundEvent.setAnnotation(updateEventRequest.getAnnotation());
        }
        if (updateEventRequest.getCategory() != null) {
            Category foundCategory = findAndCheckCategoryById(updateEventRequest.getCategory());
            foundEvent.setCategory(foundCategory);
        }
        if (updateEventRequest.getDescription() != null) {
            foundEvent.setDescription(updateEventRequest.getDescription());
        }
        if (updateEventRequest.getEventDate() != null) {
            foundEvent.setEventDate(LocalDateTime.parse(updateEventRequest.getEventDate(), GlobalVariable.TIME_FORMATTER));
        }
        if (updateEventRequest.getLocation() != null) {
            foundEvent.setLat(updateEventRequest.getLocation().getLat());
            foundEvent.setLon(updateEventRequest.getLocation().getLon());
        }
        if (updateEventRequest.getPaid() != null) {
            foundEvent.setPaid(updateEventRequest.getPaid());
        }
        if (updateEventRequest.getParticipantLimit() != null) {
            foundEvent.setParticipantLimit(updateEventRequest.getParticipantLimit());
        }
        if (updateEventRequest.getRequestModeration() != null) {
            foundEvent.setRequestModeration(updateEventRequest.getRequestModeration());
        }
        if (updateEventRequest.getTitle() != null) {
            foundEvent.setTitle(updateEventRequest.getTitle());
        }
        eventRepository.save(foundEvent);
        return EventMapper
                .toEventFullDto(
                        foundEvent,
                        eventService.getHistFromViewStats(eventId),
                        eventService.getConfirmedRequests(eventId));
    }

    @Override
    @Transactional
    public EventFullDto publishEvent(Long eventId) {
        Event foundEvent = findAndCheckEventById(eventId);
        LocalDateTime publishEvent = LocalDateTime.now();
        if (foundEvent.getState().equals(State.PENDING)) {
            checkTimeWhenPublishEvent(foundEvent.getEventDate(), publishEvent);
            foundEvent.setPublishedOn(publishEvent);
            foundEvent.setState(State.PUBLISHED);
            return EventMapper
                    .toEventFullDto(
                            foundEvent,
                            eventService.getHistFromViewStats(eventId),
                            eventService.getConfirmedRequests(eventId));
        } else {
            log.warn("The event must have the status 'PENDING' when it needs to be published.");
            throw new BadRequestException("The event must have the status 'PENDING' when it needs to be published.");
        }
    }

    @Override
    @Transactional
    public EventFullDto rejectedEvent(Long eventId) {
        Event foundEvent = findAndCheckEventById(eventId);
        if (foundEvent.getState().equals(State.PENDING)) {
            foundEvent.setState(State.CANCELED);
            log.info("Event with id={} is rejected successfully", eventId);
            return EventMapper
                    .toEventFullDto(
                            foundEvent,
                            eventService.getHistFromViewStats(eventId),
                            eventService.getConfirmedRequests(eventId));
        } else {
            log.warn("The event must have the status 'PENDING' when it needs to be rejected.");
            throw new BadRequestException("The event must have the status 'PENDING' when it needs to be rejected.");
        }
    }

    @Override
    public void deleteCommentFromEvent(Long commentId) {
        Comment foundComment = findAndCheckCommentById(commentId);
        commentRepository.delete(foundComment);
        log.info("Comment with id={} was deleted by admin successfully", commentId);
    }

    // Создания условия и возврат его для последующего запроса
    private BooleanExpression formatExpression(EventFilterAdmin eventFilterAdmin) {
        //Начальное условие
        BooleanExpression result = QEvent.event.id.gt(0);
        if (eventFilterAdmin.getUsers() != null) {
            result = result.and(QEvent.event.initiator.id.in(eventFilterAdmin.getUsers()));
        }
        if (eventFilterAdmin.getStates() != null) {
            result = result.and(QEvent.event.state.in(eventFilterAdmin.getStates()));
        }
        if (eventFilterAdmin.getCategories() != null) {
            result = result.and(QEvent.event.category.id.in(eventFilterAdmin.getCategories()));

        }
        if (eventFilterAdmin.getRangeStart() != null) {
            result = result.and(QEvent.event.eventDate.after(
                    LocalDateTime.parse(eventFilterAdmin.getRangeStart(), GlobalVariable.TIME_FORMATTER)));
        }
        if (eventFilterAdmin.getRangeEnd() != null) {
            result = result.and(QEvent.event.eventDate.before(
                    LocalDateTime.parse(eventFilterAdmin.getRangeEnd(), GlobalVariable.TIME_FORMATTER)));
        }
        return result;
    }

    // проверка на существования события и возврат, если существует
    private Event findAndCheckEventById(Long eventId) {
        Optional<Event> foundEvent = eventRepository.findById(eventId);
        if (foundEvent.isEmpty()) {
            log.warn("Event with id={} was not found.", eventId);
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        return foundEvent.get();
    }

    //
    private Comment findAndCheckCommentById(Long commentId) {
        Optional<Comment> foundComment = commentRepository.findById(commentId);
        if (foundComment.isEmpty()) {
            log.warn("Comment with id={} was not found", commentId);
            throw new NotFoundException("Comment with id=" + commentId + " was not found");
        }
        return foundComment.get();
    }

    // проверка на существования события и возврат, если существует
    private Category findAndCheckCategoryById(Long categoryId) {
        Optional<Category> foundCategory = categoryRepository.findById(categoryId);
        if (foundCategory.isEmpty()) {
            log.warn("Event with id={} was not found.", categoryId);
            throw new NotFoundException("Event with id=" + categoryId + " was not found.");
        }
        return foundCategory.get();
    }

    //Проверка условия: дата начала события должна быть не ранее чем за час от даты публикации
    private void checkTimeWhenPublishEvent(LocalDateTime eventTime, LocalDateTime publishTime) {
        if (!publishTime.plusHours(ONE_HOUR).isBefore(eventTime)) {
            log.warn("At least one hour must pass from publish time to event time");
            throw new NotFoundException("At least one hour must pass from publish time to event time");
        }
    }
}
