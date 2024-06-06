package com.vr61v;

import com.vr61v.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private static boolean validateRestaurantUpdate(
            Restaurant restaurant,
            String address,
            String phone,
            LocalTime openingHoursFrom,
            LocalTime openingHoursTo,
            Set<UUID> productIds
    ) {
        boolean result = true;

        if (address != null) result = !Objects.equals(restaurant.getAddress(), address);
        if (phone != null) result = !Objects.equals(restaurant.getPhone(), phone);
        if (openingHoursFrom != null) result = !Objects.equals(restaurant.getOpeningHoursFrom(), openingHoursFrom);
        if (openingHoursTo != null) result = !Objects.equals(restaurant.getOpeningHoursTo(), openingHoursTo);
        if (productIds != null) result = !Objects.equals(restaurant.getMenu(), productIds);

        return result;
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        // todo: сделать генерацию id в Restaurant
        restaurant.setId(UUID.randomUUID());
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
    public Restaurant updateRestaurant(
            UUID restaurantId,
            String address,
            String phone,
            LocalTime openingHoursFrom,
            LocalTime openingHoursTo,
            Set<UUID> productsIds
    ) {
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(IllegalArgumentException::new);

        if (!validateRestaurantUpdate(restaurant, address, phone, openingHoursFrom, openingHoursTo, productsIds)) {
            throw new IllegalArgumentException("the updated fields must be different from the existing ones");
        }

        if (address != null) restaurant.setAddress(address);
        if (phone != null) restaurant.setPhone(phone);
        if (openingHoursFrom != null) restaurant.setOpeningHoursFrom(openingHoursFrom);
        if (openingHoursTo != null) restaurant.setOpeningHoursTo(openingHoursTo);
        if (productsIds != null) restaurant.setMenu(restaurant.getMenu());

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant addProductsInRestaurant(UUID restaurantId, Set<UUID> productsIds) {
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(IllegalArgumentException::new);

        productsIds.forEach(restaurant::addProductInMenu);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant removeProductsFromRestaurant(UUID restaurantId, Set<UUID> productsIds) {
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(IllegalArgumentException::new);

        productsIds.forEach(restaurant::removeProductFromMenu);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(UUID restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}
