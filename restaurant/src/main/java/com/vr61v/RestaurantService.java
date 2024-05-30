package com.vr61v;

import java.sql.Time;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RestaurantService {

    Restaurant createRestaurant(Restaurant restaurant);

    Restaurant getRestaurantById(UUID restaurantId);

    List<Restaurant> getAllRestaurants();

    Restaurant updateRestaurant(UUID restaurantId, String address, String phone, Time openingHoursFrom, Time openingHoursTo, Set<UUID> productsIds);

    Restaurant addProductsInRestaurant(UUID restaurantId, Set<UUID> productIds);

    Restaurant removeProductsFromRestaurant(UUID restaurantId, Set<UUID> productIds);

    void deleteRestaurant(UUID restaurantId);

}
