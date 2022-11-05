package ru.yandex.main.admin.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.main.GlobalVariable;
import ru.yandex.main.compilation.*;
import ru.yandex.main.event.Event;
import ru.yandex.main.event.EventMapper;
import ru.yandex.main.event.EventRepository;
import ru.yandex.main.event.EventShortDto;
import ru.yandex.main.exception.NotFoundException;
import ru.yandex.main.statistic.Client;
import ru.yandex.main.statistic.ViewStats;
import ru.yandex.main.user.request.RequestService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class AdminCompilationServiceImp implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    private final RequestService requestService;
    private final Client client;

    @Override
    public CompilationDto createCompilation(@Valid NewCompilationDto newCompilationDto) {
        List<Long> eventIds = newCompilationDto.getEvents();
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto, getListEvent(eventIds));
        compilationRepository.save(compilation);
        List<EventShortDto> eventsShortDto = EventMapper
                .toEventsShortDto(
                        compilation,
                        getViews(compilation.getId()),
                        getConfirmedRequests(compilation.getId()));
        log.info("Compilation with id={} was created successfully", compilation.getId());
        return CompilationMapper.toCompilationDto(compilation, eventsShortDto);
    }

    @Override
    public void deleteCompilationById(Long compilationId) {
        Compilation foundCompilation = findAndCheckCompilationById(compilationId);
        foundCompilation.setEvents(new ArrayList<>());
        compilationRepository.deleteById(compilationId);
        log.info("Compilation with id ={} was deleted successfully", compilationId);
    }

    @Override
    public void deleteEventByIdFromCompilationById(Long compilationId, Long eventId) {
        findAndCheckEventById(eventId);
        Compilation foundCompilation = findAndCheckCompilationById(compilationId);
        List<Event> events = foundCompilation.getEvents();
        for (Event event : events) {
            if (event.getId().equals(eventId)) {
                events.remove(event);
                break;
            }
        }
        compilationRepository.save(foundCompilation);
        log.debug("Event with id={} was deleted from compilation with id={} successfully", eventId, compilationId);
    }

    @Override
    public void addEventIntoCompilation(Long compilationId, Long eventId) {
        Event foundEvent = findAndCheckEventById(eventId);
        Compilation foundCompilation = findAndCheckCompilationById(compilationId);
        foundCompilation.getEvents().add(foundEvent);
        compilationRepository.save(foundCompilation);
        log.debug("Event with id={} was added into compilation with id={} successfully", eventId, compilationId);
    }

    @Override
    public void unpinCompilation(Long compilationId) {
        Compilation foundCompilation = findAndCheckCompilationById(compilationId);
        foundCompilation.setPinned(Boolean.FALSE);
        compilationRepository.save(foundCompilation);
        log.debug("Compilation with id={} was unpin successfully", compilationId);
    }

    @Override
    public void pinCompilation(Long compilationId) {
        Compilation foundCompilation = findAndCheckCompilationById(compilationId);
        foundCompilation.setPinned(Boolean.TRUE);
        compilationRepository.save(foundCompilation);
        log.debug("Compilation with id={} was pin successfully", compilationId);
    }

    // Создания списка события для передачи в подборку, если событие не найдено, то в список не будет добавлено.
    private List<Event> getListEvent(List<Long> eventIds) {
        List<Event> events = new ArrayList<>();
        eventIds.forEach(eventId -> {
            Optional<Event> foundEvent = eventRepository.findById(eventId);
            foundEvent.ifPresentOrElse(
                    events::add,
                    () -> log.warn("Event with id={} was not found", eventId)
            );
        });
        return events;
    }

    // проверка на существования подборки и возврат, если существует
    private Compilation findAndCheckCompilationById(Long compilationId) {
        Optional<Compilation> foundCompilation = compilationRepository.findById(compilationId);
        if (foundCompilation.isEmpty()) {
            log.warn("Compilation with id={} was not found", compilationId);
            throw new NotFoundException("Compilation with id=" + compilationId + " was not found");
        }
        return foundCompilation.get();
    }

    // проверка на существования события и возврат, если существует
    private Event findAndCheckEventById(Long eventId) {
        Optional<Event> foundEvent = eventRepository.findById(eventId);
        if (foundEvent.isEmpty()) {
            log.warn("Event with id={} was not found", eventId);
            throw new NotFoundException("Event with id=" + eventId + " was not found");
        }
        return foundEvent.get();
    }

    // возврат количества просмотров у события
    private Long getViews(Long eventId) {
        String uri = "/event/" + eventId;
        Optional<ViewStats> viewStats = client.findByUrl(
                        LocalDateTime.now().minusYears(5).format(GlobalVariable.TIME_FORMATTER),
                        LocalDateTime.now().plusYears(5).format(GlobalVariable.TIME_FORMATTER),
                        uri,
                        false)
                .stream().findFirst();
        if (viewStats.isEmpty()) {
            log.info("Statistics for event with id={} were not found so 0 views are returned", eventId);
            return 0L;
        }
        return viewStats.get().getHits();
    }

    // возврат количество подтвержденных заявок по идентификатору события
    private Long getConfirmedRequests(Long eventId) {
        return requestService.getNumberOfConfirmedRequests(eventId);
    }
}
