package com.trainosys.firstspring.product;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private int nextId = 3;
    private List<Product> products = new ArrayList<>(List.of(
            new Product(1, "Mouse", 499.0, "Electronics", 15),
            new Product(2, "Keyboard", 1200.0, "Electronics", 8)
    ));

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!");
    }

    @Override
    public String createProduct(Product product) {
        if (product.getName() == null || product.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input: Valid Name and Price are required.");
        }
        product.setId(nextId++);
        products.add(product);
        return "Product created! Name: " + product.getName() + ", Price: " + product.getPrice() + " with an ID: " + product.getId();
    }

    @Override
    public String updateProduct(int id, Product updatedData) {
        if (updatedData.getName() == null || updatedData.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input fields.");
        }
        for (Product product : products) {
            if (product.getId() == id) {
                product.setName(updatedData.getName());
                product.setPrice(updatedData.getPrice());
                product.setCategory(updatedData.getCategory());
                product.setStock(updatedData.getStock());
                return "Updated product ID " + id + " with Name: " + updatedData.getName() + ", Price: " + updatedData.getPrice();
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!");
    }

    @Override
    public String deleteProduct(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                products.remove(product);
                return "Tinanggal ang product na may ID: " + id;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!");
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    @Override
    public String setProductStock(int id, int quantity) {
        if (quantity < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input: Stock quantity cannot be negative.");
        }
        for (Product product : products) {
            if (product.getId() == id) {
                product.setStock(quantity);
                return "Inupdate ang Stock ng Product ID " + id + " tungo sa: " + quantity + " pcs";
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found!");
    }
}
