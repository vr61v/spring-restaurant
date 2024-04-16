package com.vr61v.out.adapters;

import com.vr61v.out.adapters.order.OrderDetailsAdapter;
import com.vr61v.out.adapters.order.OrdersRepositoryAdapter;
import com.vr61v.out.adapters.product.CategoriesRepositoryAdapter;
import com.vr61v.out.adapters.product.ProductsRepositoryAdapter;
import com.vr61v.out.adapters.restaurant.RestaurantsRepositoryAdapter;
import com.vr61v.out.adapters.user.CardsRepositoryAdapter;
import com.vr61v.out.adapters.user.UsersRepositoryAdapter;
import com.vr61v.out.entities.order.mapper.OrderDetailMapper;
import com.vr61v.out.entities.order.mapper.OrderMapper;
import com.vr61v.out.entities.product.mappers.CategoryMapper;
import com.vr61v.out.entities.product.mappers.ProductMapper;
import com.vr61v.out.entities.restaurant.mappers.RestaurantMapper;
import com.vr61v.out.entities.user.mappers.CardMapper;
import com.vr61v.out.entities.user.mappers.UserMapper;
import com.vr61v.out.repositories.order.OrderDetailsRepository;
import com.vr61v.out.repositories.order.OrdersRepository;
import com.vr61v.out.repositories.product.CategoriesRepository;
import com.vr61v.out.repositories.product.ProductsRepository;
import com.vr61v.out.repositories.restaurant.RestaurantsRepository;
import com.vr61v.out.repositories.user.CardsRepository;
import com.vr61v.out.repositories.user.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdaptersConfiguration {
    @Bean
    public OrderDetailsAdapter orderDetailsAdapter(OrderDetailsRepository repository, OrderDetailMapper mapper) {
        return new OrderDetailsAdapter(repository, mapper);
    }

    @Bean
    public OrdersRepositoryAdapter ordersRepositoryAdapter(OrdersRepository repository, OrderMapper mapper) {
        return new OrdersRepositoryAdapter(repository, mapper);
    }

    @Bean
    public CategoriesRepositoryAdapter categoriesRepositoryAdapter(CategoriesRepository repository, CategoryMapper mapper) {
        return new CategoriesRepositoryAdapter(repository, mapper);
    }

    @Bean
    public ProductsRepositoryAdapter productsRepositoryAdapter(ProductsRepository repository, ProductMapper mapper) {
        return new ProductsRepositoryAdapter(repository, mapper);
    }

    @Bean
    public RestaurantsRepositoryAdapter restaurantsRepositoryAdapter(RestaurantsRepository repository, RestaurantMapper mapper) {
        return new RestaurantsRepositoryAdapter(repository, mapper);
    }

    @Bean
    public CardsRepositoryAdapter cardsRepositoryAdapter(CardsRepository repository, CardMapper mapper) {
        return new CardsRepositoryAdapter(repository, mapper);
    }

    @Bean
    public UsersRepositoryAdapter usersRepositoryAdapter(UsersRepository repository, UserMapper mapper) {
        return new UsersRepositoryAdapter(repository, mapper);
    }
}
