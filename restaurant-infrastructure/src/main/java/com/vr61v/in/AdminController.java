package com.vr61v.in;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.product.Category;
import com.vr61v.models.product.Product;
import com.vr61v.models.restaurant.Restaurant;
import com.vr61v.models.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User create = adminService.createUser(user);
        return new ResponseEntity<>(create, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user;

        try {
            user = adminService.getUserById(id);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable UUID id,
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "phone") String phone,
            @RequestParam(required = false, value = "email") String email
    ) {
        User update;

        try {
            update = adminService.updateUser(id, name, phone, email);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteUser(@PathVariable UUID id) {
        try {
            adminService.deleteUser(id);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }



    @PostMapping("/restaurants")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant create = adminService.createRestaurant(restaurant);
        return new ResponseEntity<>(create, HttpStatus.OK);
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable UUID id) {
        Restaurant restaurant;

        try {
            restaurant = adminService.getRestaurantById(id);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurant = adminService.getAllRestaurants();
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @PathVariable UUID id,
            @RequestParam(required = false, value = "address") String address,
            @RequestParam(required = false, value = "phone") String phone,
            @RequestParam(required = false, value = "openingHoursFrom") Time openingHoursFrom,
            @RequestParam(required = false, value = "openingHoursTo") Time openingHoursTo
    ) {
        Restaurant update;

        try {
            update = adminService.updateRestaurant(id, address, phone, openingHoursFrom, openingHoursTo);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @PostMapping("/restaurants/{id}/menu")
    public ResponseEntity<Restaurant> addProductsInRestaurant(
            @PathVariable UUID id,
            @RequestParam(required = false, value = "productIds") List<UUID> productIds
    ) {
        Restaurant update;

        try {
            update = adminService.addProductsInRestaurant(id, productIds);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/restaurants/{id}/menu")
    public ResponseEntity<Restaurant> removeProductsFromRestaurant(
            @PathVariable UUID id,
            @RequestParam(required = false, value = "productIds") List<UUID> productIds
    ) {
        Restaurant update;

        try {
            update = adminService.removeProductsFromRestaurant(id, productIds);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<UUID> deleteRestaurant(@PathVariable UUID id) {
        try {
            adminService.deleteRestaurant(id);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }


    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product create = adminService.createProduct(product);
        return new ResponseEntity<>(create, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        Product product;

        try {
            product = adminService.getProductById(id);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> product = adminService.getAllProducts();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable UUID id,
            @RequestParam(required = false, value = "categoryId") UUID categoryId,
            @RequestParam(required = false, value = "price") Integer price,
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "weight") Integer weight,
            @RequestParam(required = false, value = "composition") String composition,
            @RequestParam(required = false, value = "description") String description
    ) {
        Product update;

        try {
            update = adminService.updateProduct(id, categoryId, price, name, weight, composition, description);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<UUID> deleteProduct(@PathVariable UUID id) {
        try {
            adminService.deleteProduct(id);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }


    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category create = adminService.createCategory(category);
        return new ResponseEntity<>(create, HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
        Category category;

        try {
            category = adminService.getCategoryById(id);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = adminService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable UUID id,
            @RequestParam(required = false, value = "name") String name
    ) {
        Category update;

        try {
            update = adminService.updateCategory(id, name);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<UUID> deleteCategory(@PathVariable UUID id) {
        try {
            adminService.deleteCategory(id);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}