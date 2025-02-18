package com.creative.ekart.service.interfaces;

import com.creative.ekart.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);

    void deleteCategory(Long id);

    void updateCategory(Long id, Category category);
}
