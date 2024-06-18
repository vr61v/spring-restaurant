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
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        Product product;
        try {
            product = productService.createProduct(createProductRequest);
        } catch (Exception e) {
            log.error("Error create product {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Created product: {}", product);
        return new ResponseEntity<>(productMapper.entityToDto(product), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") UUID id) {
        Product product = productService.getProductById(id);
        log.info("Retrieved product: {}", product);
        if (product == null) return new ResponseEntity<>("The product with id:" + id + " was not found", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(productMapper.entityToDto(product), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProductsById(@RequestParam("productIds") List<UUID> productIds) {
        List<Product> products = productService.getProductsById(productIds);
        log.info("Retrieved products by id: {}", products);
        return new ResponseEntity<>(
                products.stream()
                        .map(productMapper::entityToDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        log.info("Retrieved products: {}", products);
        return new ResponseEntity<>(
                products.stream()
                        .map(productMapper::entityToDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateProductRequest updateProductRequest) {
        Product product;
        try {
            product = productService.updateProduct(id, updateProductRequest);
        } catch (Exception e) {
            log.error("Error update product: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Updated product: {}", product);
        return new ResponseEntity<>(productMapper.entityToDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID id) {
        try {
            productService.deleteProduct(id);
        } catch (Exception e) {
            log.error("Error delete product: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Deleted product: {}", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
