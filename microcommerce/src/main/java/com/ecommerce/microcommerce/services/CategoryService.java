package com.ecommerce.microcommerce.services;

import java.util.List;

import com.ecommerce.microcommerce.dtos.request.CategoryRequest;
import com.ecommerce.microcommerce.dtos.response.CategoryResponse;
import com.ecommerce.microcommerce.entities.Category;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(String categoryId, CategoryRequest request);
    List<CategoryResponse> getAllCategory();
    CategoryResponse getCategoryById(String categoryId);

    Category getCategory(String id);
}
