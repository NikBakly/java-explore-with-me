package ru.yandex.main.admin.services;

import ru.yandex.main.compilation.Compilation;

public interface AdminCompilationService {

    /**
     * Добавление новой подборки.
     *
     * @param compilation данные новой подборки
     * @return Добавленная подборка
     */
    Compilation createCompilation(
            Compilation compilation
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
    void deleteEventFromCompilation(
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
