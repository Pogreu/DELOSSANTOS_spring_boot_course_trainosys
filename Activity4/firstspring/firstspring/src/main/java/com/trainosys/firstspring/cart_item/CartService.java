package com.trainosys.firstspring.cart_item;

import java.util.List;
import java.util.Map;

public interface CartService {
    List<CartItem> getCartByUserId(int userId);
    String addItemToCart(int userId, CartItem newItem);
    String updateItemQuantity(int userId, int productId, CartItem updatedItem);
    String removeItemFromCart(int userId, int productId);
    String clearCart(int userId);
    Map<Integer, List<CartItem>> getAllCarts();
    String calculateCartTotal(int userId);
}
