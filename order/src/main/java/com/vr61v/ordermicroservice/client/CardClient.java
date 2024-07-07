package com.vr61v.ordermicroservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(
        name = "card",
        url = "http://localhost:8081"
)
public interface CardClient {

    @GetMapping("api/v1/cards/")
    ResponseEntity<?> getCard(@RequestParam(required = false) UUID id, @RequestParam(required = false) String number);

}
