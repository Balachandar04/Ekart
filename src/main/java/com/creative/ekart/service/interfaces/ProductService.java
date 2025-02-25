package com.creative.ekart.service.interfaces;

import com.creative.ekart.payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(ProductDTO product,Long categoryId);
}
