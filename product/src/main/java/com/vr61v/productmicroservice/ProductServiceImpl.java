package com.vr61v.productmicroservice;

import com.vr61v.productmicroservice.model.Product;
import com.vr61v.productmicroservice.model.request.CreateProductRequest;
import com.vr61v.productmicroservice.model.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @PreAuthorize("isAuthenticated() && hasRole(\"ADMIN\")")
    public Product createProduct(CreateProductRequest createProductRequest) {
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name(createProductRequest.name())
                .price(createProductRequest.price())
                .weight(createProductRequest.weight())
                .category(createProductRequest.category())
                .composition(createProductRequest.composition())
                .description(createProductRequest.description())
                .build();

        return productRepository.save(product);
    }

    @Override
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsById(List<UUID> productIds) {
        return productRepository.findAllById(productIds);
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasRole(\"ADMIN\")")
    public Product updateProduct(UUID productId, UpdateProductRequest updateProductRequest) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(IllegalArgumentException::new);

        if (updateProductRequest.name() != null) product.setName(updateProductRequest.name());
        if (updateProductRequest.price() != null) product.setPrice(updateProductRequest.price());
        if (updateProductRequest.weight() != null) product.setWeight(updateProductRequest.weight());
        if (updateProductRequest.category() != null) product.setCategory(updateProductRequest.category());
        if (updateProductRequest.composition() != null) product.setComposition(updateProductRequest.composition());
        if (updateProductRequest.description() != null) product.setDescription(updateProductRequest.description());

        return productRepository.save(product);
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasRole(\"ADMIN\")")
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }
}
