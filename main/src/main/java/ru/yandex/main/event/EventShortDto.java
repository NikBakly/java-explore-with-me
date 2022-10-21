package ru.yandex.main.event;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.yandex.main.category.CategoryDto;
import ru.yandex.main.user.UserShortDto;

@Data
@Builder
@ToString
public class EventShortDto {
    // Кратное описание
    String annotation;

    CategoryDto category;

    // Количество одобренных заявок на участие в данном событии
    Long confirmedRequests;

    // Дата и время создания события
    String createdOn;

    // Дата и время на которые намечено событие
    String eventDate;

    Long id;

    UserShortDto initiator;

    // Нужно ли оплачивать участие
    Boolean paid;

    // Заголовок
    String title;

    // Количество просмотрев события
    Long views;
}
