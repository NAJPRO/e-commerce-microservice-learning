package com.ecommerce.microcommerce.dtos.mapper;

import java.util.List;

import com.ecommerce.microcommerce.dtos.request.CategoryRequest;
import com.ecommerce.microcommerce.dtos.response.CategoryResponse;
import com.ecommerce.microcommerce.entities.Category;

public interface CategoryMapper {
    Category toCategory(CategoryRequest dto);
    Category toCategory(Category category, CategoryRequest dto);
    CategoryResponse toResponse(Category entity);
    List<CategoryResponse> toResponse(List<Category> entities);

}
