package com.vr61v;

import com.vr61v.model.Product;
import com.vr61v.model.ProductDto;
import com.vr61v.model.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductMapper productMapper;
    
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product = productService.createProduct(productMapper.dtoToEntity(productDto));
        return new ResponseEntity<>(productMapper.entityToDto(product), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") UUID id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(productMapper.entityToDto(product), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
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
            @RequestParam(required = false, value = "price") Integer price,
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "weight") Integer weight,
            @RequestParam(required = false, value = "categoryId") String category,
            @RequestParam(required = false, value = "composition") String composition,
            @RequestParam(required = false, value = "description") String description
    ) {
        Product product = productService.updateProduct(id, name, price, weight, category, composition, description);
        return new ResponseEntity<>(productMapper.entityToDto(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteProduct(@PathVariable("id") UUID id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
