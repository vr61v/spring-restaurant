package com.vr61v.in;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.product.Product;
import com.vr61v.models.product.Category;
import com.vr61v.models.restaurant.Restaurant;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

public interface AdminService extends BasicUserService {
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant getRestaurantById(UUID restaurantId) throws NotFoundException;
    List<Restaurant> getAllRestaurants();
    Restaurant updateRestaurant(UUID restaurantId, String address, String phone, Time openingHoursFrom, Time openingHoursTo) throws NotFoundException;
    Restaurant addProductsInRestaurant(UUID restaurantId, List<UUID> productIds) throws NotFoundException;
    Restaurant removeProductsFromRestaurant(UUID restaurantId, List<UUID> productIds) throws NotFoundException;
    UUID deleteRestaurant(UUID restaurantId) throws NotFoundException;

    Product createProduct(Product product);
    Product getProductById(UUID productId) throws NotFoundException;
    List<Product> getAllProducts();
    Product updateProduct(UUID productId, UUID categoryId, Integer price, String name, Integer weight, String composition, String description) throws NotFoundException;
    UUID deleteProduct(UUID productId) throws NotFoundException;

    Category createCategory(Category category);
    Category getCategoryById(UUID categoryId) throws NotFoundException;
    List<Category> getAllCategories();
    Category updateCategory(UUID categoryId, String name) throws NotFoundException;
    UUID deleteCategory(UUID categoryId) throws NotFoundException;
}