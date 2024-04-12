package com.vr61v.services;

import com.vr61v.models.product.Product;
import com.vr61v.models.product.Category;
import com.vr61v.models.restaurant.Restaurant;
import com.vr61v.models.user.User;

import java.sql.Time;
import java.util.Objects;

public class UpdateEntityValidator {
    public static boolean validateUserUpdate(User user, String name, String phone, String email) {
        boolean result = true;

        if (name != null) result = !Objects.equals(user.getName(), name);
        if (phone != null) result = !Objects.equals(user.getPhone(), phone);
        if (email != null) result = !Objects.equals(user.getEmail(), email);
        return result;
    }

    public static boolean validateRestaurantUpdate(Restaurant restaurant, String address, String phone, Time openingHoursFrom, Time openingHoursTo) {
        boolean result = true;

        if (address != null) result = !Objects.equals(restaurant.getAddress(), address);
        if (phone != null) result = !Objects.equals(restaurant.getPhone(), phone);
        if (openingHoursFrom != null) result = !Objects.equals(restaurant.getOpeningHoursFrom(), openingHoursFrom);
        if (openingHoursTo != null) result = !Objects.equals(restaurant.getOpeningHoursTo(), openingHoursTo);

        return result;
    }

    public static boolean validateProductUpdate(Product product, Category category, Integer price, String name, Integer weight, String description, String composition) {
        boolean result = true;

        if (name != null) result = !Objects.equals(product.getName(), name);
        if (category != null) result = !Objects.equals(product.getCategory(), category);
        if (price != null) result = !Objects.equals(product.getPrice(), price);
        if (weight != null) result = !Objects.equals(product.getWeight(), weight);
        if (description != null) result = !Objects.equals(product.getDescription(), description);
        if (composition != null) result = !Objects.equals(product.getComposition(), composition);

        return result;
    }

    public static boolean validateProductCategoryUpdate(Category category, String name) {
        boolean result = true;

        if (name != null) result = !Objects.equals(category.getName(), name);

        return result;
    }
}
