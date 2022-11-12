package ru.yandex.main.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.main.event.EventFullDto;
import ru.yandex.main.event.EventShortDto;
import ru.yandex.main.event.NewEventDto;
import ru.yandex.main.event.UpdateEventRequest;
import ru.yandex.main.user.comment.CommentService;
import ru.yandex.main.user.comment.NewCommentDto;
import ru.yandex.main.user.comment.UpdateCommentDto;
import ru.yandex.main.user.comment.ViewCommentDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}")
public class UserController {
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping("/events")
    public List<EventShortDto> findUserEventsByUserId(
            @PathVariable Long userId,
            @RequestParam(name = "from", defaultValue = "0")
            @Min(value = 0, message = "The from field cannot be negative")
            Integer from,
            @RequestParam(name = "size", defaultValue = "10")
            @Min(value = 1, message = "The size field cannot be negative or zero")
            Integer size
    ) {
        return userService.findUserEventsById(userId, from, size);
    }

    @PatchMapping("/events")
    public EventFullDto updateUserEventByUserId(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateEventRequest event
    ) {
        return userService.updateUserEventById(userId, event);
    }

    @PostMapping("/events")
    public EventFullDto createUserEvent(
            @PathVariable Long userId,
            @Valid @RequestBody NewEventDto event
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

    @PostMapping("/events/{eventId}/comments")
    public ViewCommentDto addComment(@PathVariable Long userId,
                                     @PathVariable Long eventId,
                                     @RequestBody NewCommentDto newCommentDto) {
        return commentService.addComment(userId, eventId, newCommentDto);
    }

    @PatchMapping("/events/{eventId}/comments/{commentId}")
    public ViewCommentDto updateComment(@PathVariable Long userId,
                                        @PathVariable Long eventId,
                                        @PathVariable Long commentId,
                                        @RequestBody UpdateCommentDto updateCommentDto) {
        return commentService.updateComment(userId, eventId, commentId, updateCommentDto);
    }


    @DeleteMapping("/events/{eventId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long userId,
                              @PathVariable Long eventId,
                              @PathVariable Long commentId) {
        commentService.deleteCommentById(userId, eventId, commentId);
    }
}
