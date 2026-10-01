package com.trainosys.firstspring.cart_item;

import com.trainosys.firstspring.product.Product;
import com.trainosys.firstspring.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {

    private final Map<Integer, List<CartItem>> userCarts = new HashMap<>();
    private final ProductService productService;

    public CartServiceImpl(ProductService productService) {
        this.productService = productService;

        List<CartItem> mockCart = new ArrayList<>();
        mockCart.add(new CartItem(1, 2));
        userCarts.put(1, mockCart);
    }

    @Override
    public List<CartItem> getCartByUserId(int userId) {
        return userCarts.getOrDefault(userId, new ArrayList<>());
    }

    @Override
    public String addItemToCart(int userId, CartItem newItem) {
        if (newItem.getQuantity() <= 0 || newItem.getProductId() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input: Valid Product ID and Quantity are required.");
        }

        if (!userCarts.containsKey(userId)) {
            userCarts.put(userId, new ArrayList<>());
        }

        List<CartItem> cart = userCarts.get(userId);
        boolean itemExists = false;

        for (CartItem item : cart) {
            if (item.getProductId() == newItem.getProductId()) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            cart.add(newItem);
        }

        return "Added Product ID " + newItem.getProductId() + " (Qty: " + newItem.getQuantity() + ") to User " + userId + "'s cart.";
    }

    @Override
    public String updateItemQuantity(int userId, int productId, CartItem updatedItem) {
        if (updatedItem.getQuantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input: Quantity must be greater than 0.");
        }

        if (userCarts.containsKey(userId)) {
            List<CartItem> cart = userCarts.get(userId);
            for (CartItem item : cart) {
                if (item.getProductId() == productId) {
                    item.setQuantity(updatedItem.getQuantity());
                    return "Updated item quantity for User " + userId + ", Product ID " + productId + " to " + updatedItem.getQuantity();
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart or product item not found!");
    }

    @Override
    public String removeItemFromCart(int userId, int productId) {
        if (userCarts.containsKey(userId)) {
            List<CartItem> cart = userCarts.get(userId);
            for (CartItem item : cart) {
                if (item.getProductId() == productId) {
                    cart.remove(item);
                    return "Tinanggal ang Product ID " + productId + " sa cart ni User " + userId;
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart or product item not found!");
    }

    @Override
    public String clearCart(int userId) {
        if (userCarts.containsKey(userId)) {
            userCarts.get(userId).clear();
            return "Inubos at nilinis ang buong cart ni User " + userId;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found!");
    }

    @Override
    public Map<Integer, List<CartItem>> getAllCarts() {
        return userCarts;
    }

    @Override
    public String calculateCartTotal(int userId) {
        if (!userCarts.containsKey(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found!");
        }

        List<CartItem> cart = userCarts.get(userId);
        double overallTotalPrice = 0.0;
        int totalItemsCount = 0;

        for (CartItem item : cart) {
            totalItemsCount += item.getQuantity();
            Product product = productService.getProductById(item.getProductId());
            if (product != null) {
                overallTotalPrice += (product.getPrice() * item.getQuantity());
            }
        }

        return "Kinukuha ang kabuuang halaga ng cart para kay User " + userId
                + ". Kabuuang bilang ng aytem: " + totalItemsCount
                + " pcs. Total na Presyo: ₱" + overallTotalPrice;
    }
}
