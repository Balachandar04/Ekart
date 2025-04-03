package com.creative.ekart.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long id;
    private int quantity;
    private double unitPrice;
    private int discount;
    private ProductDTO product;
    private CartDTO cart;

}
