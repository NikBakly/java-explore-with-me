package ru.yandex.main.admin.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.main.category.Category;
import ru.yandex.main.category.CategoryDto;
import ru.yandex.main.category.NewCategoryDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @PatchMapping
    CategoryDto updateCategory(
            @RequestBody Category updatedCategory
    ) {
        return adminCategoryService.updateCategory(updatedCategory);
    }

    @PostMapping
    CategoryDto createCategory(
            @RequestBody NewCategoryDto newCategory
    ) {
        return adminCategoryService.createCategory(newCategory);
    }

    @DeleteMapping("/{catId}")
    void deleteCategoryById(
            @PathVariable("catId") Long categoryId
    ) {
        adminCategoryService.deleteCategory(categoryId);
    }
}
