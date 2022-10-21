package ru.yandex.main.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEventRequest {
    // Кратное описание
    String annotation;

    //Новая категория
    Long category;

    // Полное описание события
    String description;

    // Дата и время на которые намечено событие
    String eventDate;

    Long eventId;

    // Нужно ли оплачивать участие
    Boolean paid;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    Integer participantLimit;

    // Заголовок
    String title;
}
