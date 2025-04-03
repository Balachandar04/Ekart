package com.creative.ekart.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartResposne {

    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private int totalItems;
    private boolean lastPage;
    private List<CartDTO> content;
}
