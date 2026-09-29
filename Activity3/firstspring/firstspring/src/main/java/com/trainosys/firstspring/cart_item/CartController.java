package com.trainosys.firstspring.cart_item;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private Map<Integer, List<CartItem>> userCarts = new HashMap<>();

    public CartController() {
        List<CartItem> mockCart = new ArrayList<>();
        mockCart.add(new CartItem(1, 2));
        userCarts.put(1, mockCart);
    }

    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable int userId) {
        return userCarts.getOrDefault(userId, new ArrayList<>());
    }

    @PostMapping("/{userId}/items")
    public String addItemToCart(@PathVariable int userId, @RequestBody CartItem newItem) {
        return "Added Product ID " + newItem.getProductId() + " (Qty: " + newItem.getQuantity() + ") to User " + userId + "'s cart.";
    }

    @PutMapping("/{userId}/items/{productId}")
    public String updateItemQuantity(
            @PathVariable int userId,
            @PathVariable int productId,
            @RequestBody CartItem updatedItem) {
        return "Updated item quantity for User " + userId + ", Product ID " + productId + " to " + updatedItem.getQuantity();
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public String removeItemFromCart(@PathVariable int userId, @PathVariable int productId) {
        return "Tinanggal ang Product ID " + productId + " sa cart ni User " + userId;
    }

    @DeleteMapping("/{userId}")
    public String clearCart(@PathVariable int userId) {
        return "Inubos at nilinis ang buong cart ni User " + userId;
    }

    @GetMapping("/{userId}/total")
    public String getCartTotal(@PathVariable int userId) {
        return "Kinukuha ang kabuuang halaga (Total Price) ng cart para kay User " + userId;
    }
}
