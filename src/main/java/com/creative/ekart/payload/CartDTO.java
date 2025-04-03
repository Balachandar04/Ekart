package com.creative.ekart.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private double totalPrice = 0.0;
    private double cartDiscount = 1.0;
    private List<ProductDTO> products;
}
