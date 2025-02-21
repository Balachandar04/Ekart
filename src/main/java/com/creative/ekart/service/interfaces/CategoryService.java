package com.creative.ekart.service.interfaces;

import com.creative.ekart.model.Category;
import com.creative.ekart.payload.CategoryDTO;
import com.creative.ekart.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long id);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
}
