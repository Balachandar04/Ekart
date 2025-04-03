package com.creative.ekart.service.interfaces;

import com.creative.ekart.model.Cart;
import com.creative.ekart.payload.CartDTO;
import com.creative.ekart.payload.CartItemDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

public interface CartService {

    public CartDTO getCartbyUser(UserDetails userDetails);

    public CartDTO addProductToCart(UserDetails userDetails,Long productId, int quantity);

    CartItemDTO updateQuantity(UserDetails userDetails, Long productId, Integer quantity);

    boolean removeProduct(Long productId, UserDetails userDetails);
}
