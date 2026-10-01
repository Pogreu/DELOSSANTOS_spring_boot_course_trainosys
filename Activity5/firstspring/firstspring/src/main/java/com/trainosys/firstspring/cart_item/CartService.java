package com.trainosys.firstspring.cart_item;

import java.util.List;
import java.util.Map;

public interface CartService {
    List<CartItem> getCartByUserId(Long userId);
    String addItemToCart(Long userId, CartItem newItem);
    String updateItemQuantity(Long userId, Long productId, CartItem updatedItem);
    String removeItemFromCart(Long userId, Long productId);
    String clearCart(Long userId);
    Map<Long, List<CartItem>> getAllCarts();
    String calculateCartTotal(Long userId);
}
