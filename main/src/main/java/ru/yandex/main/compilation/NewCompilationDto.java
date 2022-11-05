package ru.yandex.main.compilation;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Подборка событий
 */
@Data
@Builder
@ToString
public class NewCompilationDto {
    // Список идентификаторов событий входящих в подборку
    private List<Long> events;

    // Закреплена ли подборка на главной странице сайта
    @Builder.Default
    private Boolean pinned = Boolean.FALSE;

    // Заголовок подборки
    @NotBlank
    private String title;
}
