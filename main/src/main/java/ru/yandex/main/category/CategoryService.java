package ru.yandex.main.category;

import java.util.List;

public interface CategoryService {
    /**
     * Получение категорий
     *
     * @param from количество категорий, которые нужно пропустить для формирования текущего набора
     * @param size количество категорий в наборе
     * @return Получение информации о категории по её идентификатору
     */
    List<CategoryDto> getAll(Integer from,
                             Integer size);

    /**
     * Получение информации о категории по ее идентификатору
     *
     * @param categoryId id категории
     * @return найденная категория
     */
    CategoryDto findById(Long categoryId);
}
