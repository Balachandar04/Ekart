package com.creative.ekart.service;

import com.creative.ekart.model.Category;
import com.creative.ekart.repository.CategoryRepository;
import com.creative.ekart.service.interfaces.CategoryService;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

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
        categoryRepository.deleteById(id);

    }

    @Override
    public void updateCategory(Long id, Category category) {

        Category cat = categoryRepository.findById(id).get();
        cat.setCategoryName(category.getCategoryName());
        categoryRepository.save(cat);


    }
}
