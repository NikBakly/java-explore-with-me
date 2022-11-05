package ru.yandex.main.admin.compilation;

import ru.yandex.main.compilation.CompilationDto;
import ru.yandex.main.compilation.NewCompilationDto;

import javax.validation.Valid;

public interface AdminCompilationService {

    /**
     * Добавление новой подборки.
     *
     * @param compilation данные новой подборки
     * @return Добавленная подборка
     */
    CompilationDto createCompilation(
            @Valid NewCompilationDto compilation
    );

    /**
     * Удаление подборки.
     *
     * @param compilationId id подборки
     */
    void deleteCompilationById(
            Long compilationId
    );

    /**
     * Удаление события из подборки.
     *
     * @param compilationId id подборки
     * @param eventId       id события
     */
    void deleteEventByIdFromCompilationById(
            Long compilationId,
            Long eventId
    );

    /**
     * Добавление события в подборку.
     *
     * @param compilationId id подборки
     * @param eventId       id события
     */
    void addEventIntoCompilation(
            Long compilationId,
            Long eventId
    );

    /**
     * Открепление подборки на главной странице.
     *
     * @param compilationId id подборки
     */
    void unpinCompilation(
            Long compilationId
    );

    /**
     * Закрепить поборку на главной странице.
     *
     * @param compilationId id подборки
     */
    void pinCompilation(
            Long compilationId
    );
}
