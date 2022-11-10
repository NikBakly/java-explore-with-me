package ru.yandex.main.user.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.main.event.Event;
import ru.yandex.main.user.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // текст комментария
    @Size(max = 1000)
    private String text;

    // владелец комментария
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    // событие, которое было прокомментировано
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    // тип комментария
    @Column(name = "type_comment")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TypeOfComment typeOfComment = TypeOfComment.NEUTRAL;

    // дата и время создания
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();

    // дата и время последнего изменения
    @Builder.Default
    @Column(name = "last_update")
    private LocalDateTime lastUpdate = LocalDateTime.now();
}
