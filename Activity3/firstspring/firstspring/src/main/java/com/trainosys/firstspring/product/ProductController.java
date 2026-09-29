package com.trainosys.firstspring.product;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private List<Product> products = new ArrayList<>(List.of(
            new Product(1, "Mouse", 499.0, "Electronics", 15),
            new Product(2, "Keyboard", 1200.0, "Electronics", 8)
    ));

    @GetMapping
    public List<Product> getAllProducts() {
        return products;
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable int id) {
        return "Nakuha ang product na may ID: " + id;
    }

    @PostMapping
    public String addProduct(@RequestBody Product newProduct) {
        return "Product added! Name: " + newProduct.getName() + ", Price: " + newProduct.getPrice();
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable int id, @RequestBody Product updatedData) {
        return "Updated product ID " + id + " with Name: " + updatedData.getName();
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {
        return "Tinanggal ang product na may ID: " + id;
    }

    @GetMapping("/category/{category}")
    public String getProductsByCategory(@PathVariable String category) {
        return "Hinahanap ang mga produkto sa kategoryang: " + category;
    }

    @PutMapping("/{id}/stock/{quantity}")
    public String setProductStock(@PathVariable int id, @PathVariable int quantity) {
        return "Inupdate ang Stock ng Product ID " + id + " tungo sa: " + quantity + " pcs";
    }
}
