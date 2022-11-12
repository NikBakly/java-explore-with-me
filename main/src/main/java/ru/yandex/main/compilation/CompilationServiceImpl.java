package ru.yandex.main.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.main.event.Event;
import ru.yandex.main.event.EventMapper;
import ru.yandex.main.event.EventServiceImpl;
import ru.yandex.main.event.EventShortDto;
import ru.yandex.main.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;

    private final EventServiceImpl eventService;

    @Override
    public List<CompilationDto> getAll(Boolean pinned,
                                       Integer from,
                                       Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<Compilation> compilations = compilationRepository.findAllByPinned(pinned, pageable).getContent();
        return toCompilationsDto(compilations);
    }

    @Override
    public CompilationDto findById(Long compilationId) {
        Optional<Compilation> foundCompilation = compilationRepository.findById(compilationId);
        if (foundCompilation.isEmpty()) {
            log.warn("Compilation with id={} was not found", compilationId);
            throw new NotFoundException("Compilation with id=" + compilationId + " was not found");
        }
        List<EventShortDto> eventsShortDto = EventMapper
                .toEventsShortDto(
                        foundCompilation.get(),
                        eventService.getHistFromViewStats(foundCompilation.get().getId()),
                        eventService.getConfirmedRequests(foundCompilation.get().getId()));
        return CompilationMapper.toCompilationDto(foundCompilation.get(), eventsShortDto);
    }

    // Сопоставление списка Compilations к списку CompilationsDto
    private List<CompilationDto> toCompilationsDto(List<Compilation> compilations) {
        List<CompilationDto> result = new ArrayList<>();
        compilations.forEach(compilation -> {
            List<Long> eventIds = new ArrayList<>();
            List<Event> events = compilation.getEvents();
            events.forEach(event -> eventIds.add(event.getId()));
            List<EventShortDto> eventsShortDto =
                    EventMapper
                            .toEventsShortDto(
                                    events,
                                    eventService.getHistFromViewStats(eventIds),
                                    eventService.getConfirmedRequest(eventIds));
            result.add(CompilationMapper.toCompilationDto(compilation, eventsShortDto));
        });
        return result;
    }
}
