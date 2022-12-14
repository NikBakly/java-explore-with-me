package ru.yandex.main.admin.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.main.compilation.*;
import ru.yandex.main.event.*;
import ru.yandex.main.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminCompilationServiceImp implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    private final EventServiceImpl eventService;

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        List<Long> eventIds = newCompilationDto.getEvents();
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto, getListEvent(eventIds));
        compilationRepository.save(compilation);
        List<EventShortDto> eventsShortDto = EventMapper
                .toEventsShortDto(
                        compilation,
                        eventService.getHistFromViewStats(compilation.getId()),
                        eventService.getConfirmedRequests(compilation.getId()));
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

    // ???????????????? ???????????? ?????????????? ?????? ???????????????? ?? ????????????????
    private List<Event> getListEvent(List<Long> eventIds) {
        return eventRepository.findAllByEventIds(eventIds);
    }

    // ???????????????? ???? ?????????????????????????? ???????????????? ?? ??????????????, ???????? ????????????????????
    private Compilation findAndCheckCompilationById(Long compilationId) {
        Optional<Compilation> foundCompilation = compilationRepository.findById(compilationId);
        if (foundCompilation.isEmpty()) {
            log.warn("Compilation with id={} was not found", compilationId);
            throw new NotFoundException("Compilation with id=" + compilationId + " was not found");
        }
        return foundCompilation.get();
    }

    // ???????????????? ???? ?????????????????????????? ?????????????? ?? ??????????????, ???????? ????????????????????
    private Event findAndCheckEventById(Long eventId) {
        Optional<Event> foundEvent = eventRepository.findById(eventId);
        if (foundEvent.isEmpty()) {
            log.warn("Event with id={} was not found", eventId);
            throw new NotFoundException("Event with id=" + eventId + " was not found");
        }
        return foundEvent.get();
    }
}
