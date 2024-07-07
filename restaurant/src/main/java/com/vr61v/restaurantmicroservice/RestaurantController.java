package com.vr61v.restaurantmicroservice;

import com.vr61v.restaurantmicroservice.model.Restaurant;
import com.vr61v.restaurantmicroservice.model.RestaurantDto;
import com.vr61v.restaurantmicroservice.model.RestaurantMapper;
import com.vr61v.restaurantmicroservice.model.request.CreateRestaurantRequest;
import com.vr61v.restaurantmicroservice.model.request.UpdateRestaurantRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/restaurants")
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {
    
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    
    @PostMapping
    public ResponseEntity<?> createRestaurant(@Valid @RequestBody CreateRestaurantRequest createRestaurantRequest) {
        Restaurant restaurant;
        try {
            restaurant = restaurantService.createRestaurant(createRestaurantRequest);
        } catch (Exception e) {
            log.error("Error create restaurant {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Created restaurant {}", restaurant);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable("id") UUID id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        log.info("Retrieved restaurant {}", restaurant);
        if (restaurant == null) return new ResponseEntity<>("The restaurant with id:" + id + " was not found", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        log.info("Retrieved restaurants {}", restaurants);
        return new ResponseEntity<>(
                restaurants.stream()
                        .map(restaurantMapper::entityToDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateRestaurantRequest updateRestaurantRequest
    ) {
        Restaurant restaurant;
        try {
            restaurant = restaurantService.updateRestaurant(id, updateRestaurantRequest);
        } catch (Exception e) {
            log.error("Error update restaurant {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Updated restaurant {}", restaurant);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @PostMapping("/{id}/menu")
    public ResponseEntity<?> addProductsInRestaurant(
            @PathVariable UUID id,
            @RequestParam(value = "productIds") Set<UUID> productIds
    ) {
        Restaurant restaurant;
        try {
            restaurant = restaurantService.addProductsInRestaurant(id, productIds);
        } catch (Exception e) {
            log.error("Error add product in restaurant {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Added products {}", restaurant);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/menu")
    public ResponseEntity<?> removeProductsFromRestaurant(
            @PathVariable("id") UUID id,
            @RequestParam(value = "productIds") Set<UUID> productIds
    ) {
        Restaurant restaurant;
        try {
            restaurant = restaurantService.removeProductsFromRestaurant(id, productIds);
        } catch (Exception e) {
            log.error("Error delete product from restaurant {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Removed products {}", restaurant);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable("id") UUID id) {
        try {
            restaurantService.deleteRestaurant(id);
        } catch (Exception e) {
            log.error("Error delete restaurant {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Deleted restaurant {}", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
