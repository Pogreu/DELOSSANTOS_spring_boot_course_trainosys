package com.trainosys.firstspring.cart_item;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/api/public/carts/{userId}")
    public ResponseEntity<List<CartItem>> getCart(@PathVariable int userId) {
        return new ResponseEntity<>(cartService.getCartByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/api/public/carts/{userId}/items")
    public ResponseEntity<String> addItemToCart(@PathVariable int userId, @RequestBody CartItem newItem) {
        try {
            String status = cartService.addItemToCart(userId, newItem);
            return new ResponseEntity<>(status, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @PutMapping("/api/public/carts/{userId}/items/{productId}")
    public ResponseEntity<String> updateItemQuantity(
            @PathVariable int userId,
            @PathVariable int productId,
            @RequestBody CartItem updatedItem) {
        try {
            String status = cartService.updateItemQuantity(userId, productId, updatedItem);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @DeleteMapping("/api/public/carts/{userId}/items/{productId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable int userId, @PathVariable int productId) {
        try {
            String status = cartService.removeItemFromCart(userId, productId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @DeleteMapping("/api/public/carts/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable int userId) {
        try {
            String status = cartService.clearCart(userId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @GetMapping("/api/admin/carts")
    public ResponseEntity<Map<Integer, List<CartItem>>> getAllCarts() {
        return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
    }

    @GetMapping("/api/public/carts/{userId}/total")
    public ResponseEntity<String> getCartTotal(@PathVariable int userId) {
        try {
            String status = cartService.calculateCartTotal(userId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
