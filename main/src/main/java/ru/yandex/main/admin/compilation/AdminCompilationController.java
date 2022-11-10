package ru.yandex.main.admin.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.main.compilation.CompilationDto;
import ru.yandex.main.compilation.NewCompilationDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    @PostMapping
    public CompilationDto createCompilation(
            @Valid @RequestBody NewCompilationDto compilation
    ) {
        return adminCompilationService.createCompilation(compilation);
    }

    @DeleteMapping("/{compId}")
    void deleteCompilationById(
            @PathVariable("compId") Long compilationId
    ) {
        adminCompilationService.deleteCompilationById(compilationId);
    }

    @DeleteMapping("/{comId}/events/{eventId}")
    void deleteEventFromCompilation(
            @PathVariable("comId") Long compilationId,
            @PathVariable Long eventId
    ) {
        adminCompilationService.deleteEventByIdFromCompilationById(compilationId, eventId);
    }

    @PatchMapping("/{comId}/events/{eventId}")
    void addEventIntoCompilation(
            @PathVariable("comId") Long compilationId,
            @PathVariable Long eventId
    ) {
        adminCompilationService.addEventIntoCompilation(compilationId, eventId);
    }

    @DeleteMapping("/{comId}/pin")
    void unpinCompilation(
            @PathVariable("comId") Long compilationId
    ) {
        adminCompilationService.unpinCompilation(compilationId);
    }

    @PatchMapping("/{comId}/pin")
    void pinCompilation(
            @PathVariable("comId") Long compilationId
    ) {
        adminCompilationService.pinCompilation(compilationId);
    }

}
