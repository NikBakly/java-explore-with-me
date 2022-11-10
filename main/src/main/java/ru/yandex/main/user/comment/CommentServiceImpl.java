package ru.yandex.main.user.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.main.event.Event;
import ru.yandex.main.event.EventRepository;
import ru.yandex.main.event.State;
import ru.yandex.main.exception.BadRequestException;
import ru.yandex.main.exception.ForbiddenException;
import ru.yandex.main.exception.NotFoundException;
import ru.yandex.main.user.User;
import ru.yandex.main.user.UserRepository;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public ViewCommentDto addComment(Long userId, Long eventId, @Valid NewCommentDto newCommentDto) {
        Event foundEvent = findAndCheckEventById(eventId);
        // комментировать можно только опубликованные события
        if (!foundEvent.getState().equals(State.PUBLISHED)) {
            log.warn("User with id={} cannot comment on unpublished events", userId);
            throw new ForbiddenException("User with id=" + userId + " cannot comment on unpublished events");
        }
        User foundUser = findAndCheckUserById(userId);
        Comment comment = CommentMapper.toComment(newCommentDto, foundUser, foundEvent);
        commentRepository.save(comment);
        log.info("Comment with id={} was created successfully", comment.getId());
        ViewCommentDto result = CommentMapper.toViewCommentDto(comment);
        log.debug(result.toString());
        return result;
    }

    @Override
    public ViewCommentDto updateComment(Long userId, Long eventId, Long commentId, @Valid UpdateCommentDto updateCommentDto) {
        findAndCheckUserById(userId);
        Event foundEvent = findAndCheckEventById(eventId);
        Comment foundComment = findAndCheckCommentById(commentId);
        // проверка, что комментарий относиться к этому событию
        if (!foundComment.getEvent().getId().equals(eventId)) {
            log.warn("Comment with id={} does not belong to the event with id={}", commentId, eventId);
            throw new BadRequestException("Comment with id=" + commentId +
                    " does not belong to the event with id=" + eventId);
        }
        checkOwnerComment(foundComment, userId);
        //Процесс обновления комментария
        foundEvent.getComments().remove(foundComment);
        if (!updateCommentDto.getText().isBlank()) {
            foundComment.setText(updateCommentDto.getText());
        }
        if (updateCommentDto.getType() != null) {
            foundComment.setTypeOfComment(updateCommentDto.getType());
        }
        foundComment.setLastUpdate(LocalDateTime.now());
        foundEvent.getComments().add(foundComment);
        commentRepository.save(foundComment);
        log.info("Comment with id={} was updated successfully", foundComment.getId());
        log.debug(foundComment.toString());
        ViewCommentDto result = CommentMapper.toViewCommentDto(foundComment);
        log.debug(result.toString());
        return result;
    }

    @Override
    public void deleteCommentById(Long userId, Long eventId, Long commentId) {
        findAndCheckUserById(userId);
        findAndCheckEventById(eventId);
        Comment foundComment = findAndCheckCommentById(commentId);
        checkOwnerComment(foundComment, userId);
        commentRepository.delete(foundComment);
        log.info("Comment with id={} was deleted by user successfully", foundComment.getId());
    }

    // проверка, что владелец комментария пытается его обновить
    private void checkOwnerComment(Comment comment, Long userId) {
        // проверка, что владелец комментария пытается его обновить
        if (!comment.getOwner().getId().equals(userId)) {
            log.warn("User with id={} cannot update someone else's comment", userId);
            throw new ForbiddenException("User with id=" + userId + " cannot update someone else's comment");
        }
    }

    private Comment findAndCheckCommentById(Long commentId) {
        Optional<Comment> foundComment = commentRepository.findById(commentId);
        if (foundComment.isEmpty()) {
            log.warn("Comment with id={} was not found.", commentId);
            throw new NotFoundException("Comment with id=" + commentId + " was not found.");
        }
        return foundComment.get();
    }

    private Event findAndCheckEventById(Long eventId) {
        Optional<Event> foundEvent = eventRepository.findById(eventId);
        if (foundEvent.isEmpty()) {
            log.warn("Event with id={} was not found.", eventId);
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        return foundEvent.get();
    }

    private User findAndCheckUserById(Long userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            log.warn("User with id={} was not found.", userId);
            throw new NotFoundException("User with id=" + userId + " was not found.");
        }
        return foundUser.get();
    }
}
