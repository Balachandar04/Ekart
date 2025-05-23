package com.creative.ekart.controller;

import com.creative.ekart.config.AppConstants;
import com.creative.ekart.payload.CategoryDTO;
import com.creative.ekart.payload.CategoryResponse;
import com.creative.ekart.service.interfaces.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

//    @Autowired not required as single constructor available spring will inject automatically
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getCategories(@RequestParam(value = "pageNumber",
                                                                      defaultValue = AppConstants.PAGE_NUMBER,
                                                                      required = false) Integer pageNumber ,
                                                          @RequestParam(value="pageSize",
                                                                  defaultValue = AppConstants.PAGE_SIZE,
                                                                  required = false) Integer pageSize,
                                                          @RequestParam(value="pageSize",
                                                                  defaultValue = AppConstants.SORT_CATEGORY_BY,
                                                                  required = false) String sortBy,
                                                          @RequestParam(value="sortOrder",
                                                                  defaultValue = AppConstants.SORT_DIRECTION,
                                                                  required = false) String sortOrder) {

        CategoryResponse response = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO responseCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(responseCategoryDTO,HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/category/{category_id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable("category_id") Long id){

        CategoryDTO responseCategoryDTO = categoryService.deleteCategory(id);
        return new ResponseEntity<>(responseCategoryDTO,HttpStatus.OK);
    }

    @PutMapping("/admin/category/{category_id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("category_id") Long id,
                                                 @RequestBody CategoryDTO categoryDTO) {

        CategoryDTO responseCategory = categoryService.updateCategory(id,categoryDTO);
        return new ResponseEntity<>(responseCategory,HttpStatus.OK);

    }
}
