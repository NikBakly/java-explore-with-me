package ru.yandex.main.category;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class CategoryDto {
    // Идентификатор категории
    Long id;
    // Название категории
    String name;
}
