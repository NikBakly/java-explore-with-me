package ru.yandex.main.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.main.exception.NotFoundException;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAll(@Min(value = 0, message = "The from field cannot be negative")
                                    Integer from,
                                    @Min(value = 1, message = "The size field cannot be negative or zero")
                                    Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<Category> categories = categoryRepository.findAll(pageable).getContent();
        return CategoryMapper.toCategoriesDto(categories);
    }

    @Override
    public CategoryDto findById(Long categoryId) {
        Optional<Category> foundCategory = categoryRepository.findById(categoryId);
        if (foundCategory.isEmpty()) {
            log.warn("Category with id={} was not found", categoryId);
            throw new NotFoundException("Category with id=" + categoryId + " was not found");
        }
        return CategoryMapper.toCategoryDto(foundCategory.get());
    }
}
