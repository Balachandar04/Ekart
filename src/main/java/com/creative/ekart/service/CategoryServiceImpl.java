package com.creative.ekart.service;

import com.creative.ekart.model.Category;
import com.creative.ekart.service.interfaces.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final List<Category> categories ;

    public CategoryServiceImpl() {
        this. categories = new ArrayList<Category>();
    }

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(1000L + categories.size());
        categories.add(category);
    }

    @Override
    public void deleteCategory(Long id) {

        Category cat = categories
                .stream()
                .filter(c -> c.getCategoryId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Resource Available"));
        categories.remove(cat);

    }

    @Override
    public void updateCategory(Long id, Category category) {

        Category cat = categories
                .stream()
                .filter(c -> c.getCategoryId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Resource Available"));

        cat.setCategoryName(category.getCategoryName());


    }
}
