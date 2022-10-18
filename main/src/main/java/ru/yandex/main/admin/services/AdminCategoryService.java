package ru.yandex.main.admin.services;

import ru.yandex.main.category.Category;

public interface AdminCategoryService {

    /**
     * Изменение категории.
     *
     * @param category данные категории для изменения
     * @return Измененная категория
     */
    Category updateCategory(
            Category category
    );

    /**
     * Добавление новой категории.
     *
     * @param category данные добавляемой категории
     * @return Добавленная категория
     */
    Category createCategory(
            Category category
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
