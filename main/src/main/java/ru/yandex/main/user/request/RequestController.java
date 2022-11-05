package ru.yandex.main.user.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> findUserRequestsById(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        return requestService.findUserRequestsById(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequestById(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable("reqId") Long requestId
    ) {
        return requestService.confirmRequestById(userId, eventId, requestId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequestById(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @PathVariable("reqId") Long requestId
    ) {
        return requestService.rejectRequestById(userId, eventId, requestId);
    }

    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> findRequestsById(
            @PathVariable Long userId
    ) {
        return requestService.findRequestsById(userId);
    }

    @PostMapping("/users/{userId}/requests")
    public ParticipationRequestDto createRequest(
            @PathVariable Long userId,
            @RequestParam(name = "eventId", required = false) Long eventId
    ) {
        return requestService.createRequest(userId, eventId);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(
            @PathVariable Long userId,
            @PathVariable Long requestId
    ) {
        return requestService.cancelRequest(userId, requestId);
    }
}
