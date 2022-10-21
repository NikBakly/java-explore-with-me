package ru.yandex.main.event;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.yandex.main.Location;
import ru.yandex.main.category.CategoryDto;

@Data
@Builder
@ToString
public class AdminUpdateEventRequest {
    // Кратное описание
    String annotation;

    CategoryDto category;

    // Полное описание события
    String description;

    // Дата и время на которые намечено событие
    String eventDate;

    Location location;

    // Нужно ли оплачивать участие
    Boolean paid;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    Integer participantLimit;

    // Нужна ли пре-модерация заявок на участие
    Boolean requestModeration;

    // Заголовок
    String title;
}
