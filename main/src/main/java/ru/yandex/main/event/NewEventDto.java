package ru.yandex.main.event;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.yandex.main.Location;

/**
 * Новое событие
 */
@Data
@Builder
@ToString
public class NewEventDto {
    // Краткое описание события
    String annotation;

    // id категории к которой относится событие
    Long category;

    // Полное описание события
    String description;

    // Дата и время на которые намечено событие
    String eventDate;

    // Место проведения события
    Location location;

    // Нужно ли оплачивать участие в событии
    Boolean paid;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    Integer participantLimit;

    // Нужна ли пре-модерация заявок на участие.
    // Если true, то все заявки будут ожидать подтверждения инициатором события.
    // Если false - то будут подтверждаться автоматически.
    Boolean requestModeration;

    // Заголовок события
    String title;

}
