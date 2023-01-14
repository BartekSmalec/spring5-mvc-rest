package com.barteksmalec.spring5mvcrest.api.v1.mapper;

import com.barteksmalec.spring5mvcrest.api.v1.model.CategoryDTO;
import com.barteksmalec.spring5mvcrest.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @BeforeEach
    void setUp() {
    }

    @Test
    void categorytoCategoryDTO() {
        Category category = new Category();
        category.setName("Bartek");
        category.setId(1L);

        CategoryDTO categoryDTO = categoryMapper.categorytoCategoryDTO(category);

        assertEquals(Long.valueOf(1L), categoryDTO.getId());
        assertEquals("Bartek", categoryDTO.getName());
    }
}