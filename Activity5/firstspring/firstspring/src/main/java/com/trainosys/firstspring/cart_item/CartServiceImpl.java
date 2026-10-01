package com.trainosys.firstspring.cart_item;

import com.trainosys.firstspring.product.Product;
import com.trainosys.firstspring.product.ProductRepository;
import com.trainosys.firstspring.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartItemRepository cartItemRepository,
                           ProductRepository productRepository,
                           UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CartItem> getCartByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
        return cartItemRepository.findByUserId(userId);
    }

    @Override
    public String addItemToCart(Long userId, CartItem newItem) {
        if (newItem.getQuantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be > 0");
        }
        if (newItem.getProductId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID is required.");
        }

        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }

        productRepository.findById(newItem.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!"));

        List<CartItem> cart = cartItemRepository.findByUserId(userId);
        CartItem existingItem = null;

        for (CartItem item : cart) {
            if (item.getProductId() != null && item.getProductId().equals(newItem.getProductId())) {
                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            newItem.setUserId(userId);
            cartItemRepository.save(newItem);
        }

        return "Added Product ID " + newItem.getProductId() + " (Qty: " + newItem.getQuantity() + ") to User " + userId + "'s cart.";
    }

    @Override
    public String updateItemQuantity(Long userId, Long productId, CartItem updatedItem) {
        if (updatedItem.getQuantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be > 0");
        }

        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }

        List<CartItem> cart = cartItemRepository.findByUserId(userId);
        for (CartItem item : cart) {
            if (item.getProductId() != null && item.getProductId().equals(productId)) {
                item.setQuantity(updatedItem.getQuantity());
                cartItemRepository.save(item);
                return "Updated item quantity for User " + userId + ", Product ID " + productId + " to " + updatedItem.getQuantity();
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found!");
    }

    @Override
    public String removeItemFromCart(Long userId, Long productId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }

        List<CartItem> cart = cartItemRepository.findByUserId(userId);
        for (CartItem item : cart) {
            if (item.getProductId() != null && item.getProductId().equals(productId)) {
                cartItemRepository.delete(item);
                return "Tinanggal ang Product ID " + productId + " sa cart ni User " + userId;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found!");
    }

    @Override
    public String clearCart(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }

        List<CartItem> cart = cartItemRepository.findByUserId(userId);
        if (cart.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart is already empty!");
        }
        cartItemRepository.deleteAll(cart);
        return "Inubos at nilinis ang buong cart ni User " + userId;
    }

    @Override
    public Map<Long, List<CartItem>> getAllCarts() {
        return cartItemRepository.findAll().stream()
                .collect(Collectors.groupingBy(CartItem::getUserId));
    }

    @Override
    public String calculateCartTotal(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }

        List<CartItem> cart = cartItemRepository.findByUserId(userId);
        if (cart.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart is empty!");
        }

        double overallTotalPrice = 0.0;
        int totalItemsCount = 0;

        for (CartItem item : cart) {
            totalItemsCount += item.getQuantity();
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product in cart no longer exists!"));
            overallTotalPrice += (product.getPrice() * item.getQuantity());
        }

        return "Kinukuha ang kabuuang halaga ng cart para kay User " + userId
                + ". Kabuuang bilang ng aytem: " + totalItemsCount
                + " pcs. Total na Presyo: ₱" + overallTotalPrice;
    }
}
