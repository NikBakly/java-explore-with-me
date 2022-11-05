package ru.yandex.main.event;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.main.Location;
import ru.yandex.main.category.CategoryDto;
import ru.yandex.main.user.UserShortDto;

@Getter
@Setter
public class EventFullDto {
    // Кратное описание
    private String annotation;

    private CategoryDto category;

    // Количество одобренных заявок на участие в данном событии
    private Long confirmedRequests;

    // Дата и время создания события
    private String createdOn;

    // Полное описание события
    private String description;

    // Дата и время на которые намечено событие
    private String eventDate;

    private Long id;

    private UserShortDto initiator;

    private Location location;

    // Нужно ли оплачивать участие
    private Boolean paid;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    private Long participantLimit;

    // Дата и время публикации события
    private String publishedOn;

    // Нужна ли пре-модерация заявок на участие
    private Boolean requestModeration;

    // Список состояний жизненного цикла события
    private State state;

    // Заголовок
    private String title;

    // Количество просмотрев события
    private Long views;
}
