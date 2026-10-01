package com.trainosys.firstspring.product;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!"));
    }

    @Override
    public String createProduct(Product product) {
        if (product.getProductName() == null || product.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input: Valid Name and Price are required.");
        }
        Product savedProduct = productRepository.save(product);
        return "Product created! Name: " + savedProduct.getProductName() + ", Price: " + savedProduct.getPrice() + " with an ID: " + savedProduct.getProductId();
    }

    @Override
    public String updateProduct(Long id, Product updatedData) {
        if (updatedData.getProductName() == null || updatedData.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input fields.");
        }

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!"));

        existingProduct.setProductName(updatedData.getProductName());
        existingProduct.setPrice(updatedData.getPrice());
        existingProduct.setCategory(updatedData.getCategory());
        existingProduct.setStock(updatedData.getStock());
        productRepository.save(existingProduct);

        return "Updated product ID " + id + " with Name: " + updatedData.getProductName() + ", Price: " + updatedData.getPrice();
    }

    @Override
    public String deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!"));

        productRepository.delete(existingProduct);
        return "Tinanggal ang product na may ID: " + id;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findAll().stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @Override
    public String setProductStock(Long id, int quantity) {
        if (quantity < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input: Stock quantity cannot be negative.");
        }

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!"));

        existingProduct.setStock(quantity);
        productRepository.save(existingProduct);

        return "Inupdate ang Stock ng Product ID " + id + " tungo sa: " + quantity + " pcs";
    }
}
