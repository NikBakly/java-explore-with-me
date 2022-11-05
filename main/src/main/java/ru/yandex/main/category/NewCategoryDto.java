package ru.yandex.main.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Данные для добавления новой категории
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    // Название категории
    @NotNull
    String name;
}
