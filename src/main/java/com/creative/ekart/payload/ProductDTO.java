package com.creative.ekart.payload;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 5,message = "Product name should contains minimum 5 characters")
    private String productName;
    private String productDescription;
    private String image;
    private Integer quantity;
    private Double price;
    private Double discount;

}
