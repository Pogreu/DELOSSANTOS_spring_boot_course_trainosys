package com.trainosys.firstspring.product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(int id);
    String createProduct(Product product);
    String updateProduct(int id, Product updatedData);
    String deleteProduct(int id);
    List<Product> getProductsByCategory(String category);
    String setProductStock(int id, int quantity);
}
