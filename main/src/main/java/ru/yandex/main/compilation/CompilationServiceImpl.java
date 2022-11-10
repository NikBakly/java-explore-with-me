package ru.yandex.main.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.main.GlobalVariable;
import ru.yandex.main.event.Event;
import ru.yandex.main.event.EventMapper;
import ru.yandex.main.event.EventShortDto;
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
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;

    private final RequestService requestService;
    private final Client client;

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
                        getHistFromViewStats(foundCompilation.get().getId()),
                        getConfirmedRequests(foundCompilation.get().getId()));
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
                    EventMapper.toEventsShortDto(events, getHistFromViewStats(eventIds), getConfirmedRequest(eventIds));
            result.add(CompilationMapper.toCompilationDto(compilation, eventsShortDto));
        });
        return result;
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
