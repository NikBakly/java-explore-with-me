package ru.yandex.main.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class CompilationController {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getAll(
            @RequestParam(name = "pinned", required = false) Boolean pinned,
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "size", required = false) Integer size
    ) {
        return compilationService.getAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto findById(@PathVariable("compId") Long compilationId) {
        return compilationService.findById(compilationId);
    }
}
