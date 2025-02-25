package com.creative.ekart.service;

import com.creative.ekart.payload.ProductDTO;
import com.creative.ekart.repository.ProductRepository;
import com.creative.ekart.service.interfaces.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO,Long categoryId) {

    }

}
