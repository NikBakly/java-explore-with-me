package ru.yandex.main.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class CompilationController {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getAll(
            @RequestParam(name = "pinned", required = false) Boolean pinned,
            @RequestParam(name = "from", defaultValue = "0")
            @Min(value = 0, message = "The from field cannot be negative")
            Integer from,
            @RequestParam(name = "size", defaultValue = "10")
            @Min(value = 1, message = "The size field cannot be negative or zero")
            Integer size
    ) {
        return compilationService.getAll(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto findById(@PathVariable("compId") Long compilationId) {
        return compilationService.findById(compilationId);
    }
}
