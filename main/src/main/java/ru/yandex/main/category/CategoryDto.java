package ru.yandex.main.category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class CategoryDto {
    // Идентификатор категории
    @NotNull
    private Long id;
    // Название категории
    @NotNull
    private String name;
}
