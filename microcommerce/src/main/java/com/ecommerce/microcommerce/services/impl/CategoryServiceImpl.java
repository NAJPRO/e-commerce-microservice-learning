package com.ecommerce.microcommerce.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.microcommerce.dtos.mapper.CategoryMapper;
import com.ecommerce.microcommerce.dtos.request.CategoryRequest;
import com.ecommerce.microcommerce.dtos.response.CategoryResponse;
import com.ecommerce.microcommerce.entities.Category;
import com.ecommerce.microcommerce.exception.CategoryNotFoundException;
import com.ecommerce.microcommerce.repositories.CategoryRepositoy;
import com.ecommerce.microcommerce.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryMapper mapper;
    private final CategoryRepositoy repository;

    public CategoryServiceImpl(CategoryMapper mapper, CategoryRepositoy reposCategoryRepositoy){
        this.mapper = mapper;
        this.repository = reposCategoryRepositoy;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = mapper.toCategory(request);
        category = repository.save(category);
        return mapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category> categories = repository.findAll();
        return mapper.toResponse(categories);
    }

    @Override
    public CategoryResponse getCategoryById(String categoryId) {
        return mapper.toResponse(getCategory(categoryId));
    }

    @Override
    public CategoryResponse updateCategory(String categoryId, CategoryRequest request) {
        Category category = mapper.toCategory(getCategory(categoryId), request);
        
        return mapper.toResponse(repository.save(category));
    }

    @Override
    public Category getCategory(String id){
        Category category = repository.findById(id).orElseThrow(
            ()-> new CategoryNotFoundException(id)
        );
        return category;
    }

}
