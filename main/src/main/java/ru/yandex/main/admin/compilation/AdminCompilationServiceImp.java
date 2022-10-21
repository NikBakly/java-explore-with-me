package ru.yandex.main.admin.compilation;

import ru.yandex.main.compilation.CompilationDto;
import ru.yandex.main.compilation.NewCompilationDto;

public class AdminCompilationServiceImp implements AdminCompilationService {
    @Override
    public CompilationDto createCompilation(NewCompilationDto compilation) {
        return null;
    }

    @Override
    public void deleteCompilationById(Long compilationId) {

    }

    @Override
    public void deleteEventFromCompilation(Long compilationId, Long eventId) {

    }

    @Override
    public void addEventIntoCompilation(Long compilationId, Long eventId) {

    }

    @Override
    public void unpinCompilation(Long compilationId) {

    }

    @Override
    public void pinCompilation(Long compilationId) {

    }
}
