package com.vr61v;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product createProduct(Product product);
    Product getProductById(UUID productId);
    List<Product> getAllProducts();
    Product updateProduct(UUID productId, String name, Integer price, Integer weight, String category, String composition, String description);
    UUID deleteProduct(UUID productId);
}
