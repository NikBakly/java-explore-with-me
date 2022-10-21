package ru.yandex.main.compilation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompilationServiceImpl implements CompilationService {
    @Override
    public List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        return null;
    }

    @Override
    public CompilationDto findById(Long compilationId) {
        return null;
    }
}
