package com.creative.ekart.controller;

import com.creative.ekart.model.Category;
import com.creative.ekart.service.interfaces.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

//    @Autowired not required as single constructor available spring will inject automatically
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> c = categoryService.getAllCategories();
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @PostMapping("/admin/category")
    public ResponseEntity<String> addCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category Created",HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/category")
    public ResponseEntity<String> deleteCategory(@RequestParam("category_id") Long id){

        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
    }

    @PutMapping("/admin/category")
    public ResponseEntity<String> updateCategory(@RequestParam("category_id") Long id,
                                                 @RequestBody Category category) {
        categoryService.updateCategory(id,category);
        return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
    }
}
