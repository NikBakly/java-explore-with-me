package ru.yandex.main.compilation;

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
            Integer from,
            Integer size
    );

    /**
     * @param compilationId id подборки
     * @return Получение подборки событий по его id
     */
    CompilationDto findById(Long compilationId);
}
