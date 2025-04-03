package com.creative.ekart.controller;

import com.creative.ekart.payload.ApiResponse;
import com.creative.ekart.payload.CartDTO;
import com.creative.ekart.payload.CartItemDTO;
import com.creative.ekart.service.interfaces.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts/")
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;

    }
    @GetMapping("/users/cart")
    public ResponseEntity<CartDTO> getUserCart(@AuthenticationPrincipal UserDetails userDetails) {
        CartDTO cartDTO = cartService.getCartbyUser(userDetails);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @PostMapping("/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProduct(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long productId,
                                              @PathVariable Integer quantity) {

        CartDTO cartDTO = cartService.addProductToCart(userDetails,productId,quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);

    }

    @PatchMapping("/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartItemDTO> updateQuantity(@AuthenticationPrincipal UserDetails userDetails,
                                                      @PathVariable Long productId,
                                                      @PathVariable Integer quantity) {

        CartItemDTO cartItemDTO = cartService.updateQuantity(userDetails,productId,quantity);
        return new ResponseEntity<>(cartItemDTO,HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse> removeProduct(@AuthenticationPrincipal UserDetails userDetails,
                                                     @PathVariable Long productId) {
        boolean removed = cartService.removeProduct(productId,userDetails);
        ApiResponse apiResponse;
        if(removed){
             apiResponse = new ApiResponse("Successfully Deleted",true);
        }else{
             apiResponse = new ApiResponse("No such product exists in your cart",false);
        }

        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }
}
