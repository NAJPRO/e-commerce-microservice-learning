package com.ecommerce.microcommerce.dtos.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ecommerce.microcommerce.dtos.mapper.CategoryMapper;
import com.ecommerce.microcommerce.dtos.request.CategoryRequest;
import com.ecommerce.microcommerce.dtos.response.CategoryResponse;
import com.ecommerce.microcommerce.entities.Category;

@Component
public class CategoryMapperImpl implements CategoryMapper{

    @Override
    public Category toCategory(CategoryRequest dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }

    @Override
    public CategoryResponse toResponse(Category entity) {
        CategoryResponse cat = new CategoryResponse();
        cat.setName(entity.getName());
        cat.setId(entity.getId());
        return cat;
    }

    @Override
    public Category toCategory(Category category, CategoryRequest dto) {
        category.setName(dto.getName());
        return category;
    }

    @Override
    public List<CategoryResponse> toResponse(List<Category> entities) {
        List<CategoryResponse> responses = new ArrayList<>();
        entities.forEach(entity -> responses.add(toResponse(entity)));
        return responses;
    }

}
