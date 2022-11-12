package ru.yandex.main.user.comment;

import javax.validation.Valid;

public interface CommentService {
    /**
     * Добавление нового комментария для авторизованных пользователей.
     *
     * @param userId        идентификатор пользователя
     * @param eventId       идентификатор события
     * @param newCommentDto новый комментарий
     */
    ViewCommentDto addComment(
            Long userId,
            Long eventId,
            @Valid NewCommentDto newCommentDto);

    /**
     * Обновление комментария владельцем
     *
     * @param updateCommentDto обновленный комментарий
     * @param userId           идентификатор пользователя
     * @param eventId          идентификатор события
     */
    ViewCommentDto updateComment(
            Long userId,
            Long eventId,
            Long commentId,
            @Valid UpdateCommentDto updateCommentDto);

    /**
     * Удаление комментария владельцем
     *
     * @param eventId идентификатор события
     * @param userId  идентификатор пользователя
     */
    void deleteCommentById(Long userId, Long eventId, Long commentId);
}
