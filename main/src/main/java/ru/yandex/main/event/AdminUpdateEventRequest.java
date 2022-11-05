package ru.yandex.main.event;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.yandex.main.GlobalVariable;
import ru.yandex.main.Location;

import javax.validation.constraints.Pattern;

@Data
@Builder
@ToString
public class AdminUpdateEventRequest {
    // Кратное описание
    String annotation;

    Long category;

    // Полное описание события
    String description;

    // Дата и время на которые намечено событие
    @Pattern(regexp = GlobalVariable.PATTERN_DATE, message = "The event date field must be in a special format.")
    String eventDate;

    Location location;

    // Нужно ли оплачивать участие
    Boolean paid;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    Long participantLimit;

    // Нужна ли пре-модерация заявок на участие
    Boolean requestModeration;

    // Заголовок
    String title;
}
