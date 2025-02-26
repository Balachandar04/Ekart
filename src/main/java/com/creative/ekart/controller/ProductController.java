package com.creative.ekart.controller;

import com.creative.ekart.config.AppConstants;
import com.creative.ekart.payload.ProductDTO;
import com.creative.ekart.payload.ProductResponse;
import com.creative.ekart.service.interfaces.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("public/products")
    public ResponseEntity<ProductResponse> getProducts(@RequestParam(name = "pageNumber",
                                                              defaultValue = AppConstants.PAGE_NUMBER,
                                                              required = false) Integer pageNumber ,
                                                          @RequestParam(name = "pageSize",
                                                                  defaultValue = AppConstants.PAGE_SIZE,
                                                                  required = false)Integer pageSize,
                                                          @RequestParam(name = "orderBy",
                                                                  defaultValue = AppConstants.PRODUCT_ORDER_BY,
                                                                  required = false)String orderBy,
                                                          @RequestParam(name = "orderDir",
                                                                  defaultValue = AppConstants.SORT_DIRECTION,
                                                                  required = false)String orderDir) {
        ProductResponse response = productService.getAllProducts(pageNumber,pageSize,orderBy,orderDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable("categoryId") Long categoryId,
                                                                    @RequestParam(name = "pageNumber",
                                                                  defaultValue = AppConstants.PAGE_NUMBER,
                                                                  required = false) Integer pageNumber ,
                                                          @RequestParam(name = "pageSize",
                                                                  defaultValue = AppConstants.PAGE_SIZE,
                                                                  required = false)Integer pageSize,
                                                          @RequestParam(name = "orderBy",
                                                                  defaultValue = AppConstants.PRODUCT_ORDER_BY,
                                                                  required = false)String orderBy,
                                                          @RequestParam(name = "orderDir",
                                                                  defaultValue = AppConstants.SORT_DIRECTION,
                                                                  required = false)String orderDir) {
        ProductResponse response = productService.getAllProductsByCategory(categoryId,pageNumber,pageSize,orderBy,orderDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("public/products/keyword")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@RequestParam(name = "keyword") String keyword,
                                                                @RequestParam(name = "pageNumber",
                                                                        defaultValue = AppConstants.PAGE_NUMBER,
                                                                        required = false) Integer pageNumber ,
                                                                @RequestParam(name = "pageSize",
                                                                        defaultValue = AppConstants.PAGE_SIZE,
                                                                        required = false)Integer pageSize,
                                                                @RequestParam(name = "orderBy",
                                                                        defaultValue = AppConstants.PRODUCT_ORDER_BY,
                                                                        required = false)String orderBy,
                                                                @RequestParam(name = "orderDir",
                                                                        defaultValue = AppConstants.SORT_DIRECTION,
                                                                        required = false)String orderDir){
        ProductResponse response = productService.getAllProductsByKeyword(keyword,pageNumber,pageSize,orderBy,orderDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO,
                                                 @PathVariable("categoryId") Long categoryId) {
        ProductDTO addedProduct = productService.addProduct(productDTO, categoryId);
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);

    }

}
