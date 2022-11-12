package ru.yandex.main.event;

import lombok.*;
import ru.yandex.main.category.Category;
import ru.yandex.main.user.User;
import ru.yandex.main.user.comment.Comment;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Краткое описание события
    @Size(max = 2000)
    private String annotation;

    // id категории к которой относится событие
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    // id пользователя
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    @ToString.Exclude
    private User initiator;

    // Полное описание события
    @Size(max = 7000)
    private String description;

    // Дата и время на которые намечено событие
    @Column(name = "event_date")
    private LocalDateTime eventDate;

    // Дата и время создания события
    @Column(name = "create_on")
    private LocalDateTime createdOn;

    // Дата и время публикации события
    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    // широта место провидения
    private Double lat;

    // Долгота место проведения
    private Double lon;

    // Нужно ли оплачивать участие в событии
    private Boolean paid;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @Column(name = "participant_limit")
    private Long participantLimit;

    // Нужна ли пре-модерация заявок на участие.
    // Если true, то все заявки будут ожидать подтверждения инициатором события.
    // Если false - то будут подтверждаться автоматически.
    @Column(name = "request_moderation")
    private Boolean requestModeration;

    // Список состояний жизненного цикла события
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private State state = State.PENDING;

    // Заголовок события
    @Size(max = 120)
    private String title;

    @OneToMany(mappedBy = "event")
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();
}
