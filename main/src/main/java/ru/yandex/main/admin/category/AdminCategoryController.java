package ru.yandex.main.admin.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.main.category.CategoryDto;
import ru.yandex.main.category.NewCategoryDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @PatchMapping
    public CategoryDto updateCategory(
            @Valid @RequestBody CategoryDto updatedCategory
    ) {
        return adminCategoryService.updateCategory(updatedCategory);
    }

    @PostMapping
    public CategoryDto createCategory(
            @Valid @RequestBody NewCategoryDto newCategory
    ) {
        return adminCategoryService.createCategory(newCategory);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategoryById(
            @PathVariable("catId") Long categoryId
    ) {
        adminCategoryService.deleteCategory(categoryId);
    }
}
