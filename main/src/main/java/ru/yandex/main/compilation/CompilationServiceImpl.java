package ru.yandex.main.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.main.GlobalVariable;
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
                        getViews(foundCompilation.get().getId()),
                        getConfirmedRequests(foundCompilation.get().getId()));
        return CompilationMapper.toCompilationDto(foundCompilation.get(), eventsShortDto);
    }

    // Сопоставление списка Compilations к списку CompilationsDto
    private List<CompilationDto> toCompilationsDto(List<Compilation> compilations) {
        List<CompilationDto> result = new ArrayList<>();
        compilations.forEach(compilation -> {
            List<EventShortDto> eventsShortDto =
                    EventMapper.toEventsShortDto(
                            compilation,
                            getViews(compilation.getId()),
                            getConfirmedRequests(compilation.getId()));
            result.add(CompilationMapper.toCompilationDto(compilation, eventsShortDto));
        });
        return result;
    }

    // возврат количества просмотров у события
    private Long getViews(Long eventId) {
        String uri = "/event/" + eventId;
        Optional<ViewStats> viewStats = client.findByUrl(
                        LocalDateTime.now().minusYears(GlobalVariable.FIVE_YEAR).format(GlobalVariable.TIME_FORMATTER),
                        LocalDateTime.now().plusYears(GlobalVariable.FIVE_YEAR).format(GlobalVariable.TIME_FORMATTER),
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
