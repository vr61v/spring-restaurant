package com.vr61v;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.*;

@RestController
@RequestMapping("api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    
    private final RestaurantService restaurantService;
    
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant create = restaurantService.createRestaurant(restaurant);
        return new ResponseEntity<>(create, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable UUID id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurant = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @PathVariable UUID id,
            @RequestParam(required = false, value = "address") String address,
            @RequestParam(required = false, value = "phone") String phone,
            @RequestParam(required = false, value = "openingHoursFrom") Time openingHoursFrom,
            @RequestParam(required = false, value = "openingHoursTo") Time openingHoursTo,
            @RequestParam(required = false, value = "productIds") Set<UUID> productIds
    ) {
        Restaurant update = restaurantService.updateRestaurant(id, address, phone, openingHoursFrom, openingHoursTo, productIds);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @PostMapping("/restaurants/{id}/menu")
    public ResponseEntity<Restaurant> addProductsInRestaurant(
            @PathVariable UUID id,
            @RequestParam(required = false, value = "productIds") Set<UUID> productIds
    ) {
        Restaurant update = restaurantService.addProductsInRestaurant(id, productIds);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/restaurants/{id}/menu")
    public ResponseEntity<Restaurant> removeProductsFromRestaurant(
            @PathVariable UUID id,
            @RequestParam(required = false, value = "productIds") Set<UUID> productIds
    ) {
        Restaurant update = restaurantService.removeProductsFromRestaurant(id, productIds);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<UUID> deleteRestaurant(@PathVariable UUID id) {
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
