package ru.yandex.main.category;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Данные для добавления новой категории
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    // Название категории
    @NotBlank
    @Size(max = 511)
    private String name;
}
