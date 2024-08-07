package com.vr61v.restaurantmicroservice;

import com.vr61v.restaurantmicroservice.model.Restaurant;
import com.vr61v.restaurantmicroservice.model.request.CreateRestaurantRequest;
import com.vr61v.restaurantmicroservice.model.request.UpdateRestaurantRequest;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest);

    Restaurant getRestaurantById(UUID restaurantId);

    List<Restaurant> getAllRestaurants();

    Restaurant updateRestaurant(UUID restaurantId, UpdateRestaurantRequest updateRestaurantRequest);

    Restaurant addProductsInRestaurant(UUID restaurantId, Set<UUID> productIds);

    Restaurant removeProductsFromRestaurant(UUID restaurantId, Set<UUID> productIds);

    void deleteRestaurant(UUID restaurantId);

}
