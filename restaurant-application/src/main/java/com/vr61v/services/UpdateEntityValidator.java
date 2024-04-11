package com.vr61v.services;

import com.vr61v.models.product.Product;
import com.vr61v.models.product.ProductCategory;
import com.vr61v.models.restaurant.Restaurant;
import com.vr61v.models.user.User;

import java.sql.Time;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UpdateEntityValidator {
    public static boolean validateUserUpdate(User user, String name, String phone, String email, String password) {
        boolean result = true;

        if (name != null) result = !Objects.equals(user.getName(), name);
        if (phone != null) result = !Objects.equals(user.getPhone(), phone);
        if (email != null) result = !Objects.equals(user.getEmail(), email);
        if (password != null) result = !Objects.equals(user.getPassword(), password);
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

    public static boolean validateProductUpdate(Product product, ProductCategory productCategory, Integer price, String name, Integer weight, String description, String composition) {
        boolean result = true;

        if (name != null) result = !Objects.equals(product.getName(), name);
        if (productCategory != null) result = !Objects.equals(product.getProductCategory(), productCategory);
        if (price != null) result = !Objects.equals(product.getPrice(), price);
        if (weight != null) result = !Objects.equals(product.getWeight(), weight);
        if (description != null) result = !Objects.equals(product.getDescription(), description);
        if (composition != null) result = !Objects.equals(product.getComposition(), composition);

        return result;
    }

    public static boolean validateProductCategoryUpdate(ProductCategory productCategory, String name) {
        boolean result = true;

        if (name != null) result = !Objects.equals(productCategory.getName(), name);

        return result;
    }
}
