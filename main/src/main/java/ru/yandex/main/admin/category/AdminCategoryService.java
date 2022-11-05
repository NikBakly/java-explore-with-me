package ru.yandex.main.admin.category;

import ru.yandex.main.category.CategoryDto;
import ru.yandex.main.category.NewCategoryDto;

import javax.validation.Valid;

public interface AdminCategoryService {

    /**
     * Изменение категории.
     *
     * @param category данные категории для изменения
     * @return Измененная категория
     */
    CategoryDto updateCategory(
            @Valid CategoryDto updatedCategory
    );

    /**
     * Добавление новой категории.
     *
     * @param category данные добавляемой категории
     * @return Добавленная категория
     */
    CategoryDto createCategory(
            @Valid NewCategoryDto newCategoryDto
    );

    /**
     * Удаление категории
     *
     * @param categoryId id категории
     */
    void deleteCategory(
            Long categoryId
    );
}
