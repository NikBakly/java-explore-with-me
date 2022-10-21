package ru.yandex.main.category;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        return null;
    }

    @Override
    public CategoryDto findById(Integer categoryId) {
        return null;
    }
}
