package ru.yandex.main.user.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findCommentByOwnerIdAndEventId(Long ownerId, Long eventId);
}
