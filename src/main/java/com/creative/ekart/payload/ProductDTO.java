package com.creative.ekart.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long productId;
    private String productName;
    private String productDescription;
    private Integer quantity;
    private Double price;
    private Double discount;
    private Double specialPrice;
    private String image;

}
