package com.trainosys.firstspring.product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    String createProduct(Product product);
    String updateProduct(Long id, Product updatedData);
    String deleteProduct(Long id);
    List<Product> getProductsByCategory(String category);
    String setProductStock(Long id, int quantity);
}
