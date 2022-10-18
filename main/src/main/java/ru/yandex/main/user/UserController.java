package ru.yandex.main.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.main.event.Event;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}")
public class UserController {
    private final UserService userService;

    @GetMapping("/events")
    public List<Event> findUserEventsByUserId(
            @PathVariable Long userId,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "size", required = false) Integer size
    ) {
        return userService.findUserEventsById(userId, from, size);
    }

    @PatchMapping("/events")
    public Event updateUserEventByUserId(
            @PathVariable Long userId,
            @RequestBody Event event
    ) {
        return userService.updateUserEventById(userId, event);
    }

    @PostMapping("/events")
    public Event createUserEvent(
            @PathVariable Long userId,
            @RequestBody Event event
    ) {
        return userService.createUserEvent(userId, event);
    }

    @GetMapping("/events/{eventId}")
    public Event findUserEventByUserIdAndByEventId(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        return userService.findUserEventByUserIdAndByEventId(userId, eventId);

    }

    @PatchMapping("/events/{eventId}")
    public Event eventCancellation(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        return userService.eventCancellation(userId, eventId);
    }

    @GetMapping("/events/{eventId}/requests")
    public Request findUserRequestById(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        return userService.findUserRequestById(userId, eventId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public Request confirmRequestById(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable("reqId") Long requestId
    ) {
        return userService.confirmRequestById(userId, eventId, requestId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    public Request rejectRequestById(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable("reqId") Long requestId
    ) {
        return userService.rejectRequestById(userId, eventId, requestId);
    }

    @GetMapping("/requests")
    public List<Request> findRequestsById(
            @PathVariable Long userId
    ) {
        return userService.findRequestsById(userId);
    }

    @PostMapping("/requests")
    public Request createRequest(
            @PathVariable Long userId,
            @RequestParam(name = "eventId", required = false) Long eventId
    ) {
        return userService.createRequest(userId, eventId);
    }

    @PostMapping("/requests/{requestId}/cancel")
    public Request cancelRequest(
            @PathVariable Long userId,
            @PathVariable Long requestId
    ) {
        return userService.cancelRequest(userId, requestId);
    }
}
