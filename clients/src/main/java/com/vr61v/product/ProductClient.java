package com.vr61v.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "product",
        url = "http://localhost:8083"
)
public interface ProductClient {

    @GetMapping(path = "api/v1/products/")
    ResponseEntity<List<ProductDto>> getProductsById(@RequestParam("productIds") List<UUID> productIds);

}
