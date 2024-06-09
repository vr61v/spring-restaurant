package com.vr61v;

import com.vr61v.model.Product;
import com.vr61v.model.request.CreateProductRequest;
import com.vr61v.model.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private static boolean validateProductUpdate(Product product, UpdateProductRequest updateProductRequest) {
        boolean result = true;

        if (updateProductRequest.name() != null) result &= !Objects.equals(product.getName(), updateProductRequest.name());
        if (updateProductRequest.price() != null) result &= !Objects.equals(product.getPrice(), updateProductRequest.price());
        if (updateProductRequest.weight() != null) result &= !Objects.equals(product.getWeight(), updateProductRequest.weight());
        if (updateProductRequest.category() != null) result &= !Objects.equals(product.getCategory(), updateProductRequest.category());
        if (updateProductRequest.description() != null) result &= !Objects.equals(product.getDescription(), updateProductRequest.description());
        if (updateProductRequest.composition() != null) result &= !Objects.equals(product.getComposition(), updateProductRequest.composition());

        return result;
    }

    @Override
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
    public Product updateProduct(UUID productId, UpdateProductRequest updateProductRequest) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(IllegalArgumentException::new);

        if (!validateProductUpdate(product, updateProductRequest)) {
            throw new IllegalArgumentException("the updated fields must be different from the existing ones");
        }

        if (updateProductRequest.name() != null) product.setName(updateProductRequest.name());
        if (updateProductRequest.price() != null) product.setPrice(updateProductRequest.price());
        if (updateProductRequest.weight() != null) product.setWeight(updateProductRequest.weight());
        if (updateProductRequest.category() != null) product.setCategory(updateProductRequest.category());
        if (updateProductRequest.description() != null) product.setDescription(updateProductRequest.description());
        if (updateProductRequest.composition() != null) product.setComposition(updateProductRequest.composition());

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }
}
