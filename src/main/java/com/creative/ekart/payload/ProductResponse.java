package com.creative.ekart.payload;

import com.creative.ekart.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponse {
    private List<Product> content;
}
