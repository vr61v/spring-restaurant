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
    Restaurant addProductInRestaurant(UUID restaurantId, UUID productId) throws NotFoundException;
    Restaurant addProductsInRestaurant(UUID restaurantId, List<UUID> productIds) throws NotFoundException;
    Restaurant removeProductFromRestaurant(UUID restaurantId, UUID productId) throws NotFoundException;
    Restaurant removeProductsFromRestaurant(UUID restaurantId, List<UUID> productIds) throws NotFoundException;
    UUID deleteRestaurant(UUID restaurantId) throws NotFoundException;

    Product createProduct(Product product);
    Product getProductById(UUID productId) throws NotFoundException;
    List<Product> getAllProducts();
    Product updateProduct(UUID productId, UUID productCategoryId, Integer price, String name, Integer weight, String composition, String description) throws NotFoundException;
    UUID deleteProduct(UUID productId) throws NotFoundException;

    Category createProductCategory(Category category);
    Category getProductCategoryById(UUID productCategoryId) throws NotFoundException;
    List<Category> getAllProductCategory();
    Category updateProductCategory(UUID productCategoryId, String name) throws NotFoundException;
    UUID deleteProductCategory(UUID productCategoryId) throws NotFoundException;
}