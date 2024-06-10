package com.vr61v;

import com.vr61v.model.Restaurant;
import com.vr61v.model.RestaurantDto;
import com.vr61v.model.RestaurantMapper;
import com.vr61v.model.request.CreateRestaurantRequest;
import com.vr61v.model.request.UpdateRestaurantRequest;
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
    public ResponseEntity<RestaurantDto> createRestaurant(@Valid @RequestBody CreateRestaurantRequest createRestaurantRequest) {
        Restaurant restaurant = restaurantService.createRestaurant(createRestaurantRequest);
        log.info("Created restaurant {}", restaurant);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable("id") UUID id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        log.info("Retrieved restaurant {}", restaurant);
        if (restaurant == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        log.info("Retrieved restaurants {}", restaurants);
        if (restaurants.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(
                restaurants.stream()
                        .map(restaurantMapper::entityToDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateRestaurantRequest updateRestaurantRequest
    ) {
        Restaurant restaurant;
        try {
            restaurant = restaurantService.updateRestaurant(id, updateRestaurantRequest);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Updated restaurant {}", restaurant);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @PostMapping("/{id}/menu")
    public ResponseEntity<RestaurantDto> addProductsInRestaurant(
            @PathVariable UUID id,
            @RequestParam(value = "productIds") Set<UUID> productIds
    ) {
        Restaurant restaurant;
        try {
            restaurant = restaurantService.addProductsInRestaurant(id, productIds);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Added products {}", restaurant);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/menu")
    public ResponseEntity<RestaurantDto> removeProductsFromRestaurant(
            @PathVariable("id") UUID id,
            @RequestParam(value = "productIds") Set<UUID> productIds
    ) {
        Restaurant restaurant;
        try {
            restaurant = restaurantService.removeProductsFromRestaurant(id, productIds);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Removed products {}", restaurant);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteRestaurant(@PathVariable("id") UUID id) {
        try {
            restaurantService.deleteRestaurant(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Deleted restaurant {}", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
