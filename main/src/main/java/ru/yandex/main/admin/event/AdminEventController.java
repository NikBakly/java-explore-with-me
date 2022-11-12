package ru.yandex.main.admin.event;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.main.GlobalVariable;
import ru.yandex.main.event.AdminUpdateEventRequest;
import ru.yandex.main.event.EventFullDto;
import ru.yandex.main.event.State;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {
    private final AdminEventService adminEventService;

    @GetMapping
    public List<EventFullDto> findEvents(
            @RequestParam(name = "users", required = false) List<Long> users,
            @RequestParam(name = "states", required = false) List<State> states,
            @RequestParam(name = "categories", required = false) List<Long> categories,
            @RequestParam(name = "rangeStart", required = false)
            @Pattern(regexp = GlobalVariable.PATTERN_DATE, message = "The range start field must be in a special format.")
            String rangeStart,
            @RequestParam(name = "rangeEnd", required = false)
            @Pattern(regexp = GlobalVariable.PATTERN_DATE, message = "The range end field must be in a special format.")
            String rangeEnd,
            @RequestParam(name = "from", defaultValue = "0")
            @Min(value = 0, message = "The from field cannot be negative")
            Integer from,
            @RequestParam(name = "size", defaultValue = "10")
            @Min(value = 1, message = "The size field cannot be negative or zero")
            Integer size
    ) {

        return adminEventService.findEvents(
                EventFilterAdmin.builder()
                        .users(users)
                        .states(states)
                        .categories(categories)
                        .rangeStart(rangeStart)
                        .rangeEnd(rangeEnd)
                        .from(from)
                        .size(size)
                        .build()
        );
    }

    @PutMapping("/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable Long eventId,
            @RequestBody AdminUpdateEventRequest event
    ) {
        return adminEventService.updateEvent(
                eventId,
                event
        );
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publishEvent(
            @PathVariable Long eventId
    ) {
        return adminEventService.publishEvent(
                eventId
        );
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto rejectedEvent(
            @PathVariable Long eventId
    ) {
        return adminEventService.rejectedEvent(
                eventId
        );
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(
            @PathVariable Long commentId
    ) {
        adminEventService.deleteCommentFromEvent(commentId);
    }

}
