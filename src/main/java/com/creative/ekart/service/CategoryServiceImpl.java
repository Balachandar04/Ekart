package com.creative.ekart.service;

import com.creative.ekart.exception.ResourceNotFoundException;
import com.creative.ekart.model.Category;
import com.creative.ekart.repository.CategoryRepository;
import com.creative.ekart.service.interfaces.CategoryService;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    public void updateCategory(Long id, Category category) {

        Category existingCategory = categoryRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found"));
        existingCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(existingCategory);
    }
}
