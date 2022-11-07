package ru.yandex.main.user.comment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.main.GlobalVariable;
import ru.yandex.main.event.Event;
import ru.yandex.main.user.User;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommentMapper {

    public static Comment toComment(NewCommentDto newCommentDto, User user, Event event) {
        Comment comment = new Comment();
        comment.setText(newCommentDto.getText());
        comment.setOwner(user);
        comment.setEvent(event);
        if (newCommentDto.getType() != null) {
            comment.setTypeOfComment(newCommentDto.getType());
        }
        return comment;
    }

    public static ViewCommentDto toViewCommentDto(Comment comment) {
        return ViewCommentDto.builder()
                .text(comment.getText())
                .ownerName(comment.getOwner().getName())
                .ownerEmail(comment.getOwner().getEmail())
                .type(comment.getTypeOfComment())
                .eventUrl("/events/" + comment.getEvent().getId())
                .lastUpdate(comment.getLastUpdate().format(GlobalVariable.TIME_FORMATTER))
                .build();
    }

    public static List<ViewCommentDto> toViewCommentsDto(List<Comment> comments) {
        List<ViewCommentDto> result = new ArrayList<>();
        comments.forEach(comment -> result.add(toViewCommentDto(comment)));
        return result;
    }

}
