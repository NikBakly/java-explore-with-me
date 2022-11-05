package ru.yandex.main.compilation;

import javax.validation.constraints.Min;
import java.util.List;

public interface CompilationService {

    /**
     * @param pinned искать только закрепленные/не закрепленные подборки
     * @param from   количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size   количество элементов в наборе
     * @return Получение подборок событий.
     */
    List<CompilationDto> getAll(
            Boolean pinned,
            @Min(value = 0, message = "The from field cannot be negative")
            Integer from,
            @Min(value = 1, message = "The size field cannot be negative or zero")
            Integer size
    );

    /**
     * @param compilationId id подборки
     * @return Получение подборки событий по его id
     */
    CompilationDto findById(Long compilationId);
}
