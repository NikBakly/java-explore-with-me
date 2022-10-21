package ru.yandex.main.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.main.event.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}")
public class UserController {
    private final UserService userService;

    @GetMapping("/events")
    public List<EventShortDto> findUserEventsByUserId(
            @PathVariable Long userId,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "size", required = false) Integer size
    ) {
        return userService.findUserEventsById(userId, from, size);
    }

    @PatchMapping("/events")
    public UpdateEventRequest updateUserEventByUserId(
            @PathVariable Long userId,
            @RequestBody Event event
    ) {
        return userService.updateUserEventById(userId, event);
    }

    @PostMapping("/events")
    public EventFullDto createUserEvent(
            @PathVariable Long userId,
            @RequestBody NewEventDto event
    ) {
        return userService.createUserEvent(userId, event);
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto findUserEventByUserIdAndByEventId(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        return userService.findUserEventByUserIdAndByEventId(userId, eventId);

    }

    @PatchMapping("/events/{eventId}")
    public EventFullDto eventCancellation(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        return userService.eventCancellation(userId, eventId);
    }

    @GetMapping("/events/{eventId}/requests")
    public ParticipationRequestDto findUserRequestById(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        return userService.findUserRequestById(userId, eventId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequestById(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable("reqId") Long requestId
    ) {
        return userService.confirmRequestById(userId, eventId, requestId);
    }

    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequestById(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable("reqId") Long requestId
    ) {
        return userService.rejectRequestById(userId, eventId, requestId);
    }

    @GetMapping("/requests")
    public List<ParticipationRequestDto> findRequestsById(
            @PathVariable Long userId
    ) {
        return userService.findRequestsById(userId);
    }

    @PostMapping("/requests")
    public ParticipationRequestDto createRequest(
            @PathVariable Long userId,
            @RequestParam(name = "eventId", required = false) Long eventId
    ) {
        return userService.createRequest(userId, eventId);
    }

    @PostMapping("/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(
            @PathVariable Long userId,
            @PathVariable Long requestId
    ) {
        return userService.cancelRequest(userId, requestId);
    }
}
