package ru.yandex.main.compilation;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.yandex.main.event.EventShortDto;

import java.util.List;

@Data
@Builder
@ToString
public class CompilationDto {
    // Список событий входящих в подборку
    List<EventShortDto> events;

    Long id;

    // Закреплена ли подборка на главной странице сайта
    Boolean pinned;

    // Заголовок подборки
    String title;
}
