package com.creative.ekart.repository;

import com.creative.ekart.model.Category;
import com.creative.ekart.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategory(Category category, Pageable pageable);
    Page<Product> findAllByProductNameContainingIgnoreCase(String productName,Pageable pageable);
}
