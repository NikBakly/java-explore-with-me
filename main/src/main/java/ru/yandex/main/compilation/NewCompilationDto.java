package ru.yandex.main.compilation;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Подборка событий
 */
@Data
@Builder
@ToString
public class NewCompilationDto {
    // Список идентификаторов событий входящих в подборку
    List<Long> events;

    // Закреплена ли подборка на главной странице сайта
    Boolean pinned;

    // Заголовок подборки
    String title;
}