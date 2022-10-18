package ru.yandex.main.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAll(
            @RequestParam(name = "from", required = false) Integer from,
            @RequestParam(name = "size", required = false) Integer size
    ) {
        return categoryService.getAll(from, size);
    }

    @GetMapping("/{catId}")
    public Category findById(
            @PathVariable("catId") Integer categoryId
    ) {
        return categoryService.findById(categoryId);
    }

}
