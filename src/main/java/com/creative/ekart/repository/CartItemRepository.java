package com.creative.ekart.repository;

import com.creative.ekart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem as ci WHERE ci.cart.cartId=?1 and ci.product.productId=?2")
    CartItem findByCartIdAndProductId(Long cartId, Long productId);
}
