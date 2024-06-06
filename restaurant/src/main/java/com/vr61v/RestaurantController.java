package com.vr61v;

import com.vr61v.model.Restaurant;
import com.vr61v.model.RestaurantDto;
import com.vr61v.model.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    
    private final RestaurantService restaurantService;

    private final RestaurantMapper restaurantMapper;
    
    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantService.createRestaurant(restaurantMapper.dtoToEntity(restaurantDto));
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable("id") UUID id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(
                restaurants.stream()
                        .map(restaurantMapper::entityToDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    // todo: Разобраться почему не передается productIds
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(
            @PathVariable UUID id,
            @RequestParam(required = false, value = "address") String address,
            @RequestParam(required = false, value = "phone") String phone,
            @RequestParam(required = false, value = "openingHoursFrom") LocalTime openingHoursFrom,
            @RequestParam(required = false, value = "openingHoursTo") LocalTime openingHoursTo,
            @RequestParam(required = false, value = "productIds") Set<UUID> productIds
    ) {
        Restaurant restaurant = restaurantService.updateRestaurant(id, address, phone, openingHoursFrom, openingHoursTo, productIds);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @PostMapping("/{id}/menu")
    public ResponseEntity<RestaurantDto> addProductsInRestaurant(
            @PathVariable UUID id,
            @RequestParam(required = false, value = "productIds") Set<UUID> productIds
    ) {
        Restaurant restaurant = restaurantService.addProductsInRestaurant(id, productIds);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/menu")
    public ResponseEntity<RestaurantDto> removeProductsFromRestaurant(
            @PathVariable("id") UUID id,
            @RequestParam(required = false, value = "productIds") Set<UUID> productIds
    ) {
        Restaurant restaurant = restaurantService.removeProductsFromRestaurant(id, productIds);
        return new ResponseEntity<>(restaurantMapper.entityToDto(restaurant), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteRestaurant(@PathVariable("id") UUID id) {
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
