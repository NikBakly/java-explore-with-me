package ru.yandex.main.event;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.main.GlobalVariable;
import ru.yandex.main.exception.NotFoundException;
import ru.yandex.main.statistic.Client;
import ru.yandex.main.statistic.EndpointHit;
import ru.yandex.main.statistic.ViewStats;
import ru.yandex.main.user.request.RequestService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    private final RequestService requestService;
    private final Client client;

    @Value("${main-service.name}")
    private static String MAIN_APP;

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getAll(EventFilter eventFilter, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(
                eventFilter.getFrom(),
                eventFilter.getSize());
        List<Event> foundEvent = eventRepository.findAll(formatExpression(eventFilter), pageable).getContent();
        List<Long> eventIds = new ArrayList<>();
        foundEvent.forEach(event -> eventIds.add(event.getId()));
        List<EventShortDto> result = EventMapper.toEventsShortDto(foundEvent, getHistFromViewStats(eventIds), getConfirmedRequest(eventIds));
        if (eventFilter.getSort() != null) {
            sortEvents(result, eventFilter.getSort());
        }
        createStatistic(MAIN_APP, request.getRequestURI(), request.getRemoteAddr());
        log.info("Events were got all successfully");
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto findById(Long eventId, HttpServletRequest request) {
        Optional<Event> foundEvent = eventRepository.findEventByIdAndStateIsPublished(eventId);
        if (foundEvent.isEmpty()) {
            log.warn("Event with id={} was not found.", eventId);
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        log.info("Event with id={} was found successfully", eventId);
        createStatistic(MAIN_APP, request.getRequestURI(), request.getRemoteAddr());
        return EventMapper.toEventFullDto(foundEvent.get(), getHistFromViewStats(eventId), getConfirmedRequests(eventId));
    }

    //  ???????????????????????? ?????????????? ?????? ?????????????? ?? ????
    private BooleanExpression formatExpression(EventFilter filter) {
        //?????????????????? ???? ?????????????? ????, ?????? ?????? ???????????? ?????????????????? ???????????????????????????? ??????????????;
        BooleanExpression result = QEvent.event.state.eq(State.PUBLISHED);
        if (filter.getText() != null) {
            result = result.or(QEvent.event.description.likeIgnoreCase(filter.getText()));
            result = result.or(QEvent.event.annotation.likeIgnoreCase(filter.getText()));
        }
        if (filter.getCategories() != null) {
            result = result.and(QEvent.event.category.id.in(filter.getCategories()));
        }
        if (filter.getPaid() != null) {
            result = result.and(QEvent.event.paid.eq(filter.getPaid()));
        }
        if (filter.getRangeStart() != null) {
            LocalDateTime dateTime = LocalDateTime.parse(filter.getRangeStart(), GlobalVariable.TIME_FORMATTER);
            result = result.and(QEvent.event.eventDate.after(dateTime));
        }
        if (filter.getRangeEnd() != null) {
            LocalDateTime dateTime = LocalDateTime.parse(filter.getRangeEnd(), GlobalVariable.TIME_FORMATTER);
            result = result.and(QEvent.event.eventDate.before(dateTime));
        }
        if (filter.getRangeStart() == null && filter.getRangeEnd() == null) {
            result = result.and(QEvent.event.eventDate.after(LocalDateTime.now()));
        }
        if (filter.getOnlyAvailable().equals(Boolean.TRUE)) {
            result = result.and(QEvent.event.participantLimit.goe(getConfirmedRequests(Long.valueOf(QEvent.event.id.toString()))));
        }
        return result;
    }

    // ???????????????????? ???????????? ?????????????? ???? ?????????????????????? ???????? ?? ???? ??????????????????????
    private void sortEvents(List<EventShortDto> eventsShortDto, TypesOfSort types) {
        switch (types) {
            case EVENT_DATE:
                eventsShortDto.sort((o1, o2) -> {
                    LocalDateTime time1 = LocalDateTime.parse(o1.getEventDate(), GlobalVariable.TIME_FORMATTER);
                    LocalDateTime time2 = LocalDateTime.parse(o2.getEventDate(), GlobalVariable.TIME_FORMATTER);
                    return time1.compareTo(time2);
                });
                break;
            case VIEWS:
                eventsShortDto.sort((o1, o2) -> (int) (o1.getViews() - o2.getViews()));
                break;
        }
    }

    // ?????????? ?????? ???????????????? ????????????????????
    private void createStatistic(String app, String uri, String ip) {
        String timestamp = LocalDateTime.now().format(GlobalVariable.TIME_FORMATTER);
        EndpointHit endpointHit = EndpointHit.builder()
                .app(app)
                .uri(uri)
                .ip(ip)
                .timestamp(timestamp)
                .build();
        client.addStatistic(endpointHit);
    }

    // ?????????????? ???????????????????? ???????????????????? ?? ??????????????
    public Long getHistFromViewStats(Long eventId) {
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

    public List<Long> getHistFromViewStats(List<Long> eventIds) {
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
            // ?????????????????? ???????????? ?????????? ????????????
            eventIds.forEach(aLong -> hits.add(0L));
        } else {
            // ?????????????????? ??????????????
            viewStats.forEach(viewStats1 -> hits.add(viewStats1.getHits()));
        }
        return hits;
    }

    // ?????????????? ???????????????????? ???????????????????????????? ???????????? ???? ???????????????????????????? ??????????????
    public Long getConfirmedRequests(Long eventId) {
        return requestService.getNumberOfConfirmedRequests(eventId);
    }

    public List<Long> getConfirmedRequest(List<Long> eventIds) {
        return requestService.getNumberOfConfirmedRequests(eventIds);
    }

}
