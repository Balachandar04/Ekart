package com.creative.ekart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String description;
    private String image;
    private Integer quantity;
    private Double price;
    private Double discount;

    @ManyToOne
    @JoinColumn(name = "cateogry_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.MERGE,CascadeType.REMOVE}, fetch = FetchType.EAGER)
    List<CartItem> cartItemList ;

}
