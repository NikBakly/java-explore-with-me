package ru.yandex.main.category;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Данные для добавления новой категории
 */
@Data
@Builder
@ToString
public class NewCategoryDto {
    // Название категории
    String name;
}
