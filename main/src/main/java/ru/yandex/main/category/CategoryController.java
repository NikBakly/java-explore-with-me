package ru.yandex.main.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAll(
            @RequestParam(name = "from", defaultValue = "0")
            @Min(value = 0, message = "The from field cannot be negative")
            Integer from,
            @RequestParam(name = "size", defaultValue = "1")
            @Min(value = 1, message = "The size field cannot be negative or zero")
            Integer size
    ) {
        return categoryService.getAll(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto findById(
            @PathVariable("catId") Long categoryId
    ) {
        return categoryService.findById(categoryId);
    }

}
