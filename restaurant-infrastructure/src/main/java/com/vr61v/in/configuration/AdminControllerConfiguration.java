package com.vr61v.in.configuration;

import com.vr61v.in.AdminService;
import com.vr61v.out.adapters.product.CategoriesRepositoryAdapter;
import com.vr61v.out.adapters.product.ProductsRepositoryAdapter;
import com.vr61v.out.adapters.restaurant.RestaurantsRepositoryAdapter;
import com.vr61v.out.adapters.user.UsersRepositoryAdapter;
import com.vr61v.services.AdminServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminControllerConfiguration {
    @Bean
    public AdminService adminService(
            RestaurantsRepositoryAdapter restaurantsRepositoryAdapter,
            ProductsRepositoryAdapter productsRepositoryAdapter,
            CategoriesRepositoryAdapter categoriesRepositoryAdapter,
            UsersRepositoryAdapter usersRepositoryAdapter) {
        return new AdminServiceImpl(
                restaurantsRepositoryAdapter,
                productsRepositoryAdapter,
                categoriesRepositoryAdapter,
                usersRepositoryAdapter
        );
    }
}
