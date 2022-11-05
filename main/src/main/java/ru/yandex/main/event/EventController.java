package ru.yandex.main.event;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getAll(
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "categories", required = false) List<Long> categories,
            @RequestParam(name = "paid", required = false) Boolean paid,
            @RequestParam(name = "rangeStart", required = false) String rangeStart,
            @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
            @RequestParam(name = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(name = "sort", required = false) TypesOfSort sort,
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            HttpServletRequest request
    ) {
        EventFilter filter = new EventFilter(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return eventService.getAll(filter, request);
    }

    @GetMapping("/{eventId}")
    public EventFullDto findById(
            @PathVariable("eventId") Long eventId,
            HttpServletRequest request
    ) {
        return eventService.findById(eventId, request);
    }
}
