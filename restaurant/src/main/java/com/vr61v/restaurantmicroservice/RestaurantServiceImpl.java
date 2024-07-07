package com.vr61v.restaurantmicroservice;

import com.vr61v.restaurantmicroservice.model.Restaurant;
import com.vr61v.restaurantmicroservice.model.request.CreateRestaurantRequest;
import com.vr61v.restaurantmicroservice.model.request.UpdateRestaurantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    @PreAuthorize("isAuthenticated() && hasRole(\"ADMIN\")")
    public Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest) {
        Restaurant restaurant = Restaurant.builder()
                .id(UUID.randomUUID())
                .address(createRestaurantRequest.address())
                .phone(createRestaurantRequest.phone())
                .openingHoursFrom(createRestaurantRequest.openingHoursFrom())
                .openingHoursTo(createRestaurantRequest.openingHoursTo())
                .menu(createRestaurantRequest.menu())
                .build();

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant getRestaurantById(UUID restaurantId) {
        return restaurantRepository.findById(restaurantId).orElse(null);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasRole(\"ADMIN\")")
    public Restaurant updateRestaurant(UUID restaurantId, UpdateRestaurantRequest updateRestaurantRequest ) {
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(IllegalArgumentException::new);

        if (updateRestaurantRequest.address() != null) restaurant.setAddress(updateRestaurantRequest.address());
        if (updateRestaurantRequest.phone() != null) restaurant.setPhone(updateRestaurantRequest.phone());
        if (updateRestaurantRequest.openingHoursFrom() != null) restaurant.setOpeningHoursFrom(updateRestaurantRequest.openingHoursFrom());
        if (updateRestaurantRequest.openingHoursTo() != null) restaurant.setOpeningHoursTo(updateRestaurantRequest.openingHoursTo());
        if (updateRestaurantRequest.menu() != null) restaurant.setMenu(updateRestaurantRequest.menu());

        return restaurantRepository.save(restaurant);
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasRole(\"ADMIN\")")
    public Restaurant addProductsInRestaurant(UUID restaurantId, Set<UUID> productsIds) {
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(IllegalArgumentException::new);

        productsIds.forEach(restaurant::addProductInMenu);

        return restaurantRepository.save(restaurant);
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasRole(\"ADMIN\")")
    public Restaurant removeProductsFromRestaurant(UUID restaurantId, Set<UUID> productsIds) {
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(IllegalArgumentException::new);

        productsIds.forEach(restaurant::removeProductFromMenu);

        return restaurantRepository.save(restaurant);
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasRole(\"ADMIN\")")
    public void deleteRestaurant(UUID restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}
