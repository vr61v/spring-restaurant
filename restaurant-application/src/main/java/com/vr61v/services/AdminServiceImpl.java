package com.vr61v.services;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.in.AdminService;
import com.vr61v.models.product.Product;
import com.vr61v.models.product.Category;
import com.vr61v.models.restaurant.Restaurant;
import com.vr61v.models.user.types.Role;
import com.vr61v.models.user.User;
import com.vr61v.out.product.Categories;
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
    private final Categories categories;
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
        restaurants.save(restaurant);
        return restaurant;
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

        restaurants.save(restaurant);
        return restaurant;
    }

    @Override
    public Restaurant removeProductFromRestaurant(UUID restaurantId, UUID productId) throws NotFoundException {
        Restaurant restaurant = restaurants.find(restaurantId);
        Product product = products.find(productId);

        if (!restaurant.getMenu().contains(product)) {
            throw new IllegalArgumentException("This restaurant does not have this product {" + productId + "} on the menu.");
        }

        restaurant.removeProductFromMenu(product);
        restaurants.save(restaurant);
        return restaurant;
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
        products.save(product);
        return product;
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
    public Product updateProduct(UUID productId, UUID categoryId, Integer price, String name, Integer weight, String composition, String description) throws NotFoundException {
        Product product = products.find(productId);
        Category category = categories.find(categoryId);
        if (!UpdateEntityValidator.validateProductUpdate(product, category, price, name, weight, description, composition)) {
            throw new IllegalArgumentException("The updated fields must be different from the existing ones.");
        }

        if (name != null) product.setName(name);
        if (categoryId != null) product.setCategory(category);
        if (price != null) product.setPrice(price);
        if (weight != null) product.setWeight(weight);
        if (description != null) product.setDescription(description);
        if (composition != null) product.setComposition(composition);

        products.save(product);
        return product;
    }

    @Override
    public UUID deleteProduct(UUID productId) throws NotFoundException {
        products.delete(productId);
        return productId;
    }

    @Override
    public Category createCategory(Category category) {
        categories.save(category);
        return category;
    }

    @Override
    public Category getCategoryById(UUID categoryId) throws NotFoundException {
        return categories.find(categoryId);
    }

    @Override
    public List<Category> getAllCategories() {
        return categories.findAll();
    }

    @Override
    public Category updateCategory(UUID categoryId, String name) throws NotFoundException {
        Category category = categories.find(categoryId);
        if (!UpdateEntityValidator.validateProductCategoryUpdate(category, name)) {
            throw new IllegalArgumentException("The updated fields must be different from the existing ones.");
        }

        if (name != null) category.setName(name);
        categories.save(category);
        return category;
    }

    @Override
    public UUID deleteCategory(UUID categoryId) throws NotFoundException {
        categories.delete(categoryId);
        return categoryId;
    }

    @Override
    public User createUser(User user) {
        if (user.getRole() == Role.USER) {
            throw new IllegalArgumentException("Admin can create only admins and cooks.");
        }
        users.save(user);
        return user;
    }

    @Override
    public User getUserById(UUID userId) throws NotFoundException {
        return users.find(userId);
    }

    @Override
    public User updateUser(UUID userId, String name, String phone, String email) throws NotFoundException {
        User update = users.find(userId);

        if (!UpdateEntityValidator.validateUserUpdate(update, name, phone, email)) {
            throw new IllegalArgumentException("The updated fields must be different from the existing ones.");
        }

        if (name != null) update.setName(name);
        if (phone != null) update.setPhone(phone);
        if (email != null) update.setEmail(email);

        return update;
    }

    @Override
    public UUID deleteUser(UUID userId) throws NotFoundException {
        users.delete(userId);
        return userId;
    }
}
