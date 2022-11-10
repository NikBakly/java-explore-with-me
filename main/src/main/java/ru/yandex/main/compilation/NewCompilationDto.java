package ru.yandex.main.compilation;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Подборка событий
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    // Список идентификаторов событий входящих в подборку
    private List<Long> events;

    // Закреплена ли подборка на главной странице сайта
    @Builder.Default
    private Boolean pinned = Boolean.FALSE;

    // Заголовок подборки
    @NotBlank
    @Size(max = 511)
    private String title;
}
