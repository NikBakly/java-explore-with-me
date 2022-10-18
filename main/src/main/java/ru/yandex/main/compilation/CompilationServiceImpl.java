package ru.yandex.main.compilation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompilationServiceImpl implements CompilationService {
    @Override
    public List<Compilation> getAll(Boolean pinned, Integer from, Integer size) {
        return null;
    }

    @Override
    public Compilation findById(Long compilationId) {
        return null;
    }
}
