package com.vr61v.services;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.in.AdminService;
import com.vr61v.models.product.Product;
import com.vr61v.models.product.ProductCategory;
import com.vr61v.models.restaurant.Restaurant;
import com.vr61v.models.user.Role;
import com.vr61v.models.user.User;
import com.vr61v.out.product.ProductCategories;
import com.vr61v.out.product.Products;
import com.vr61v.out.restaurant.Restaurants;
import com.vr61v.out.user.Users;
import lombok.RequiredArgsConstructor;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final Restaurants restaurants;
    private final Products products;
    private final ProductCategories productCategories;
    private final Users users;

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurants.save(restaurant);
    }

    @Override
    public Restaurant getRestaurantById(UUID restaurantId) throws NotFoundException {
        return restaurants.find(restaurantId);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurants.findAll();
    }

    @Override
    public Restaurant updateRestaurant(UUID restaurantId, String address, String phone, Time openingHoursFrom, Time openingHoursTo) throws NotFoundException {
        Restaurant restaurant = restaurants.find(restaurantId);
        if (!UpdateEntityValidator.validateRestaurantUpdate(restaurant, address, phone, openingHoursFrom, openingHoursTo)) {
            throw new IllegalArgumentException("The updated fields must be different from the existing ones.");
        }

        if (address != null) restaurant.setAddress(address);
        if (phone != null) restaurant.setPhone(phone);
        if (openingHoursFrom != null) restaurant.setOpeningHoursFrom(openingHoursFrom);
        if (openingHoursTo != null) restaurant.setOpeningHoursTo(openingHoursTo);

        return restaurants.save(restaurant);
    }

    @Override
    public Restaurant addProductInRestaurant(UUID restaurantId, UUID productId) throws NotFoundException {
        Restaurant restaurant = restaurants.find(restaurantId);
        Product product = products.find(productId);

        if (restaurant.getMenu().contains(product)) {
            throw new IllegalArgumentException("The product {" + productId + "} is already on the menu.");
        }

        restaurant.addProductInMenu(product);

        return restaurants.save(restaurant);
    }

    @Override
    public Restaurant addProductsInRestaurant(UUID restaurantId, List<UUID> productIds) throws NotFoundException {
        Restaurant restaurant = restaurants.find(restaurantId);
        List<Product> productList = new ArrayList<>();
        for (UUID i : productIds) productList.add(products.find(i));

        for (Product i : productList){
            if (restaurant.getMenu().contains(i)) {
                throw new IllegalArgumentException("The product {" + i.getId() + "} is already on the menu.");
            }
            restaurant.addProductInMenu(i);
        }

        return restaurants.save(restaurant);
    }

    @Override
    public Restaurant removeProductFromRestaurant(UUID restaurantId, UUID productId) throws NotFoundException {
        Restaurant restaurant = restaurants.find(restaurantId);
        Product product = products.find(productId);

        if (!restaurant.getMenu().contains(product)) {
            throw new IllegalArgumentException("This restaurant does not have this product {" + productId + "} on the menu.");
        }

        restaurant.removeProductFromMenu(product);

        return restaurants.save(restaurant);
    }

    @Override
    public Restaurant removeProductsFromRestaurant(UUID restaurantId, List<UUID> productIds) throws NotFoundException {
        Restaurant restaurant = restaurants.find(restaurantId);
        List<Product> productList = new ArrayList<>();
        for (UUID i : productIds) productList.add(products.find(i));

        for (Product i : productList){
            if (!restaurant.getMenu().contains(i)) {
                throw new IllegalArgumentException("This restaurant does not have this product {" + i.getId() + "} on the menu.");
            }
            restaurant.removeProductFromMenu(i);
        }

        return restaurants.save(restaurant);
    }

    @Override
    public UUID deleteRestaurant(UUID restaurantId) throws NotFoundException {
        restaurants.delete(restaurantId);
        return restaurantId;
    }

    @Override
    public Product createProduct(Product product) {
        return products.save(product);
    }

    @Override
    public Product getProductById(UUID productId) throws NotFoundException {
        return products.find(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return products.findAll();
    }

    @Override
    public Product updateProduct(UUID productId, UUID productCategoryId, Integer price, String name, Float weight, String composition, String description) throws NotFoundException {
        Product product = products.find(productId);
        ProductCategory productCategory = productCategories.find(productCategoryId);
        if (!UpdateEntityValidator.validateProductUpdate(product, productCategory, price, name, weight, description, composition)) {
            throw new IllegalArgumentException("The updated fields must be different from the existing ones.");
        }

        if (name != null) product.setName(name);
        if (productCategoryId != null) product.setProductCategory(productCategory);
        if (price != null) product.setPrice(price);
        if (weight != null) product.setWeight(weight);
        if (description != null) product.setDescription(description);
        if (composition != null) product.setComposition(composition);

        return products.save(product);
    }

    @Override
    public UUID deleteProduct(UUID productId) throws NotFoundException {
        products.delete(productId);
        return productId;
    }

    @Override
    public ProductCategory createProductCategory(ProductCategory productCategory) {
        return productCategories.save(productCategory);
    }

    @Override
    public ProductCategory getProductCategoryById(UUID productCategoryId) throws NotFoundException {
        return productCategories.find(productCategoryId);
    }

    @Override
    public List<ProductCategory> getAllProductCategory() {
        return productCategories.findAll();
    }

    @Override
    public ProductCategory updateProductCategory(UUID productCategoryId, String name) throws NotFoundException {
        ProductCategory productCategory = productCategories.find(productCategoryId);
        if (!UpdateEntityValidator.validateProductCategoryUpdate(productCategory, name)) {
            throw new IllegalArgumentException("The updated fields must be different from the existing ones.");
        }

        if (name != null) productCategory.setName(name);

        return productCategories.save(productCategory);
    }

    @Override
    public UUID deleteProductCategory(UUID productCategoryId) throws NotFoundException {
        productCategories.delete(productCategoryId);
        return productCategoryId;
    }

    @Override
    public User createUser(User user) {
        if (user.getRole() == Role.USER) {
            throw new IllegalArgumentException("Admin can create only admins and cooks.");
        }
        return users.save(user);
    }

    @Override
    public User getUserById(UUID userId) throws NotFoundException {
        return users.find(userId);
    }

    @Override
    public User updateUser(UUID userId, String name, String phone, String email, String password) throws NotFoundException {
        User update = users.find(userId);

        if (!UpdateEntityValidator.validateUserUpdate(update, name, phone, email, password)) {
            throw new IllegalArgumentException("The updated fields must be different from the existing ones.");
        }

        if (name != null) update.setName(name);
        if (phone != null) update.setPhone(phone);
        if (email != null) update.setEmail(email);
        if (password != null) update.setPassword(password);

        return update;
    }

    @Override
    public UUID deleteUser(UUID userId) throws NotFoundException {
        users.delete(userId);
        return userId;
    }
}
