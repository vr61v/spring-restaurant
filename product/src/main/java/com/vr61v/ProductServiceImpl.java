package com.vr61v;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private static boolean validateProductUpdate(Product product, String name, Integer price, Integer weight, String category, String composition, String description) {
        boolean result = true;

        if (name != null) result = !Objects.equals(product.getName(), name);
        if (price != null) result = !Objects.equals(product.getPrice(), price);
        if (weight != null) result = !Objects.equals(product.getWeight(), weight);
        if (category != null) result = !Objects.equals(product.getCategory(), category);
        if (description != null) result = !Objects.equals(product.getDescription(), description);
        if (composition != null) result = !Objects.equals(product.getComposition(), composition);

        return result;
    }

    @Override
    public Product createProduct(Product product) {
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
    public Product updateProduct(UUID productId, String name, Integer price, Integer weight, String category, String composition, String description) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(IllegalArgumentException::new);

        if (!validateProductUpdate(product, name, price, weight, category, description, composition)) {
            throw new IllegalArgumentException("The updated fields must be different from the existing ones.");
        }

        if (name != null) product.setName(name);
        if (price != null) product.setPrice(price);
        if (weight != null) product.setWeight(weight);
        if (category != null) product.setCategory(category);
        if (description != null) product.setDescription(description);
        if (composition != null) product.setComposition(composition);

        return productRepository.save(product);
    }

    @Override
    public UUID deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
        return productId;
    }
}
