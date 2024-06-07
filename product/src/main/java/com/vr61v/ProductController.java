package com.vr61v;

import com.vr61v.model.Product;
import com.vr61v.model.ProductDto;
import com.vr61v.model.ProductMapper;
import com.vr61v.model.request.CreateProductRequest;
import com.vr61v.model.request.UpdateProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    private final ProductMapper productMapper;
    
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        Product product = productService.createProduct(createProductRequest);
        log.info("Created product: {}", product);
        return new ResponseEntity<>(productMapper.entityToDto(product), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") UUID id) {
        Product product = productService.getProductById(id);
        log.info("Retrieved product: {}", product);
        if (product == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(productMapper.entityToDto(product), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        log.info("Retrieved products: {}", products);
        if (products.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(
                products.stream()
                        .map(productMapper::entityToDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateProductRequest updateProductRequest) {
        Product product;
        try {
            product = productService.updateProduct(id, updateProductRequest);
        } catch (Exception e) {
            log.warn("Failed to update product: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Updated product: {}", product);
        return new ResponseEntity<>(productMapper.entityToDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteProduct(@PathVariable("id") UUID id) {
        productService.deleteProduct(id);
        log.info("Deleted product: {}", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
