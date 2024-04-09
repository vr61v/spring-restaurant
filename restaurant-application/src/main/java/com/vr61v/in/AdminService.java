package com.vr61v.in;

import com.vr61v.models.product.Product;
import com.vr61v.models.product.ProductCategory;
import com.vr61v.models.restaurant.Restaurant;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

public interface AdminService extends BasicUserService {
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant getRestaurantById(UUID restaurantId);
    List<Restaurant> getAllRestaurants();
    Restaurant updateRestaurant(UUID restaurantId, String address, String phone, Time openingHoursFrom, Time openingHoursTo, List<Product> products);
    UUID deleteRestaurant(UUID restaurantId);

    Product createProduct(Product product);
    Product getProductById(UUID productId);
    List<Product> getAllProducts();
    Product updateProduct(UUID productId, UUID productCategoryId, Integer price, String name, Float weight, String composition, String description);
    UUID deleteProduct(UUID productId);

    ProductCategory createProductCategory(ProductCategory productCategory);
    ProductCategory getProductCategoryById(UUID productCategoryId);
    List<ProductCategory> getAllProductCategory();
    ProductCategory updateProductCategory(UUID productCategoryId, String name);
    UUID deleteProductCategory(UUID productCategoryId);
}