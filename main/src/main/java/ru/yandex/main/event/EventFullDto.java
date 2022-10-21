package ru.yandex.main.event;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.yandex.main.Location;
import ru.yandex.main.category.CategoryDto;
import ru.yandex.main.user.UserShortDto;

@Data
@Builder
@ToString
public class EventFullDto {
    // Кратное описание
    String annotation;

    CategoryDto category;

    // Количество одобренных заявок на участие в данном событии
    Long confirmedRequests;

    // Дата и время создания события
    String createdOn;

    // Полное описание события
    String description;

    // Дата и время на которые намечено событие
    String eventDate;

    Long id;

    UserShortDto initiator;

    Location location;

    // Нужно ли оплачивать участие
    Boolean paid;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    Integer participantLimit;

    // Дата и время публикации события
    String publishedOn;

    // Нужна ли пре-модерация заявок на участие
    Boolean requestModeration;

    // Список состояний жизненного цикла события
    State state;

    // Заголовок
    String title;

    // Количество просмотрев события
    Long views;
}
