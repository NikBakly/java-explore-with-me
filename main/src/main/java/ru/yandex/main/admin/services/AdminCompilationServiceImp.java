package ru.yandex.main.admin.services;

import ru.yandex.main.compilation.Compilation;

public class AdminCompilationServiceImp implements AdminCompilationService {
    @Override
    public Compilation createCompilation(Compilation compilation) {
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
