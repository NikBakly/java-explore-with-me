package ru.yandex.main.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.main.category.Category;

import javax.persistence.*;

@Entity
@Table(name = "events")
@Getter
@Setter
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // Краткое описание события
    String annotation;

    // id категории к которой относится событие
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    Category category;

    // id пользователя
    Long initiatorId;

    // Полное описание события
    String description;

    // Дата и время на которые намечено событие
    String eventDate;

    // широта место провидения
    Double lat;

    // Долгота место проведения
    Double lon;

    // Нужно ли оплачивать участие в событии
    @Builder.Default
    Boolean paid = false;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @Builder.Default
    Integer participantLimit = 0;

    // Нужна ли пре-модерация заявок на участие.
    // Если true, то все заявки будут ожидать подтверждения инициатором события.
    // Если false - то будут подтверждаться автоматически.
    @Builder.Default
    Boolean requestModeration = true;

    // Заголовок события
    String title;
}
