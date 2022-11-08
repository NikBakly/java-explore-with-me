package ru.yandex.main.event;

import lombok.*;
import ru.yandex.main.GlobalVariable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {
    // Кратное описание
    @Size(min = 20, max = 2000, message = "The annotation field should be between 20 and 2000 in length")
    private String annotation;

    //Новая категория
    private Long category;

    // Полное описание события
    @Size(min = 20, max = 7000, message = "The description field should be between 20 and 2000 in length")
    private String description;

    // Дата и время на которые намечено событие
    @Pattern(regexp = GlobalVariable.PATTERN_DATE, message = "The event date field must be in a special format.")
    private String eventDate;

    @NotNull
    private Long eventId;

    // Нужно ли оплачивать участие
    private Boolean paid;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    private Long participantLimit;

    // Заголовок
    @Size(min = 3, max = 120, message = "The title field should be between 20 and 2000 in length")
    private String title;
}
