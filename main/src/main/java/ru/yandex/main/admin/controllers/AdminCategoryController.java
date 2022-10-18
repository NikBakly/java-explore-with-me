package ru.yandex.main.admin.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.main.admin.services.AdminCategoryService;
import ru.yandex.main.category.Category;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @PatchMapping
    Category updateCategory(
            @RequestBody Category updatedCategory
    ) {
        return adminCategoryService.updateCategory(updatedCategory);
    }

    @PostMapping
    Category createCategory(
            @RequestBody Category newCategory
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
