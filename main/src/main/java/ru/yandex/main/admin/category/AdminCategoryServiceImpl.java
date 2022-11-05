package ru.yandex.main.admin.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.main.category.*;
import ru.yandex.main.exception.NotFoundException;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto updateCategory(@Valid CategoryDto updatedCategory) {
        Optional<Category> foundCategory = categoryRepository.findById(updatedCategory.getId());

        if (foundCategory.isEmpty()) {
            log.warn("Category with id={} was not found", updatedCategory.getId());
            throw new NotFoundException("Category with id=" + updatedCategory.getId() + " was not found");
        }

        if (updatedCategory.getName() != null) {
            foundCategory.get().setName(updatedCategory.getName());
        }
        categoryRepository.save(foundCategory.get());
        log.info("Category with id={} is updated successfully", foundCategory.get().getId());
        return CategoryMapper.toCategoryDto(foundCategory.get());
    }

    @Override
    public CategoryDto createCategory(@Valid NewCategoryDto newCategoryDto) {
        Category newCategory = CategoryMapper.toCategory(newCategoryDto);
        categoryRepository.save(newCategory);
        log.info("Category with id={} is created successfully", newCategory.getId());
        return CategoryMapper.toCategoryDto(newCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.findById(categoryId).ifPresentOrElse(
                category -> {
                    categoryRepository.deleteById(categoryId);
                    log.info("Category with id={} is deleted successfully", categoryId);
                },
                () -> {
                    log.warn("Category with id={} was not found", categoryId);
                    throw new NotFoundException("Category with id=" + categoryId + " was not found");
                }
        );
    }
}
