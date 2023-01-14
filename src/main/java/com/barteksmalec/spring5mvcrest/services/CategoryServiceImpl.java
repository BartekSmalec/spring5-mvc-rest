package com.barteksmalec.spring5mvcrest.services;

import com.barteksmalec.spring5mvcrest.api.v1.mapper.CategoryMapper;
import com.barteksmalec.spring5mvcrest.api.v1.model.CategoryDTO;
import com.barteksmalec.spring5mvcrest.repositories.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categorytoCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return categoryMapper.categorytoCategoryDTO(categoryRepository.findByName(name));
    }
}
