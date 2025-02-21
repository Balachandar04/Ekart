package com.creative.ekart.service;

import com.creative.ekart.exception.ApiException;
import com.creative.ekart.exception.ResourceNotFoundException;
import com.creative.ekart.model.Category;
import com.creative.ekart.payload.CategoryDTO;
import com.creative.ekart.payload.CategoryResponse;
import com.creative.ekart.repository.CategoryRepository;
import com.creative.ekart.service.interfaces.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private  ModelMapper mapper;
    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private ModelMapper modelMapper;
    CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();
        if(allCategories.isEmpty()) {
            throw new ApiException("No Category found");
        }
        List<CategoryDTO> categoryDTOS =
                allCategories
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO,Category.class);
        category.setCategoryId(null);
        Category selectedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(selectedCategory != null) {
             throw new ApiException("Category with name "+category.getCategoryName()+" already exists");
        }
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("category", "id", id));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO,Category.class);
        Category existingCategory = categoryRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("category", "id", id));
        existingCategory.setCategoryName(category.getCategoryName());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }
}
