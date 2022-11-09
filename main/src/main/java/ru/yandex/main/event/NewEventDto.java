package ru.yandex.main.event;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.yandex.main.GlobalVariable;
import ru.yandex.main.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Новое событие
 */
@Data
@Builder
@ToString
public class NewEventDto {
    // Краткое описание события
    @Size(min = 20, max = 2000, message = "The title field must be between 20 and 2000 in length")
    @NotBlank
    private String annotation;

    // id категории к которой относится событие
    @NotBlank(message = "The category field cannot be undefined")
    private Long category;

    // Полное описание события
    @Size(min = 20, max = 7000, message = "The description field must be between 20 and 7000 in length")
    @NotBlank(message = "The description field cannot be undefined")
    private String description;

    // Дата и время на которые намечено событие
    @Pattern(regexp = GlobalVariable.PATTERN_DATE, message = "The event date field must be in a special format.")
    @NotBlank(message = "The event date field cannot be undefined")
    private String eventDate;

    // Место проведения события
    @NotNull(message = "The Location field cannot be undefined")
    private Location location;

    // Нужно ли оплачивать участие в событии
    @Builder.Default
    private Boolean paid = false;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @Builder.Default
    private Long participantLimit = 0L;

    // Нужна ли пре-модерация заявок на участие.
    // Если true, то все заявки будут ожидать подтверждения инициатором события.
    // Если false - то будут подтверждаться автоматически.
    @Builder.Default
    private Boolean requestModeration = true;

    // Заголовок события
    @Size(min = 3, max = 120, message = "The title field must be between 3 and 120 in length")
    @NotBlank(message = "The title field cannot be blank")
    private String title;

}
