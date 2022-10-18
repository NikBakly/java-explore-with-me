package ru.yandex.main.admin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.main.admin.services.AdminEventService;
import ru.yandex.main.event.Event;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/events")
public class AdminEventController {
    private final AdminEventService adminEventService;

    @GetMapping
    public List<Event> findEvents(
            @RequestParam(name = "users", required = false) int[] users,
            @RequestParam(name = "states", required = false) String[] states,
            @RequestParam(name = "categories", required = false) int[] categories,
            @RequestParam(name = "rangeStart", required = false) String rangeStart,
            @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "size", required = false) Integer size
    ) {
        return adminEventService.findEvents(
                users,
                states,
                categories,
                rangeStart,
                rangeEnd,
                from,
                size
        );
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(
            @PathVariable Long eventId,
            @RequestBody Event event
    ) {
        return adminEventService.updateEvent(
                eventId,
                event
        );
    }

    @PatchMapping("/{eventId}/publish")
    public Event publishEvent(
            @PathVariable Long eventId
    ) {
        return adminEventService.publishEvent(
                eventId
        );
    }

    @PatchMapping("/{eventId}/rejected")
    public Event rejectedEvent(
            @PathVariable Long eventId
    ) {
        return adminEventService.rejectedEvent(
                eventId
        );
    }

}
