package com.creative.ekart.service.interfaces;

import com.creative.ekart.payload.ProductDTO;
import com.creative.ekart.payload.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(ProductDTO product,Long categoryId);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String orderBy,String orderDirection);

    ProductResponse getAllProductsByCategory(Long categoryId,Integer pageNumber,Integer pageSize,String orderBy,String orderDirection);

    ProductResponse getAllProductsByKeyword(String keyword,Integer pageNumber,Integer pageSize,String orderBy,String orderDirection);

    ProductDTO updateProductById(Long productId, @Valid ProductDTO productDTO);

    ProductDTO deleteProductById(Long productId);

    ProductDTO updateImage(Long productId, MultipartFile image) throws IOException;
}
