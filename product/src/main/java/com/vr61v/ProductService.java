package com.vr61v;

import com.vr61v.model.Product;
import com.vr61v.model.request.CreateProductRequest;
import com.vr61v.model.request.UpdateProductRequest;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product createProduct(CreateProductRequest createProductRequest);

    Product getProductById(UUID productId);

    List<Product> getAllProducts();

    List<Product> getProductsById(List<UUID> productIds);

    Product updateProduct(UUID productId, UpdateProductRequest updateProductRequest);

    void deleteProduct(UUID productId);

}
