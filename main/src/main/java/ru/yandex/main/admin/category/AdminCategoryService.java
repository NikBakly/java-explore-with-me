package ru.yandex.main.admin.category;

import ru.yandex.main.category.CategoryDto;
import ru.yandex.main.category.NewCategoryDto;

public interface AdminCategoryService {

    /**
     * Изменение категории.
     *
     * @param category данные категории для изменения
     * @return Измененная категория
     */
    CategoryDto updateCategory(
            CategoryDto updatedCategory
    );

    /**
     * Добавление новой категории.
     *
     * @param category данные добавляемой категории
     * @return Добавленная категория
     */
    CategoryDto createCategory(
            NewCategoryDto newCategoryDto
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
