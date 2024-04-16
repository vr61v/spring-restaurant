package com.vr61v.in;

import com.vr61v.out.adapters.order.OrdersRepositoryAdapter;
import com.vr61v.out.adapters.user.CardsRepositoryAdapter;
import com.vr61v.out.adapters.user.UsersRepositoryAdapter;
import com.vr61v.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserControllerConfig {
    @Bean
    public UserService userService(
            UsersRepositoryAdapter usersRepositoryAdapter,
            OrdersRepositoryAdapter ordersRepositoryAdapter,
            CardsRepositoryAdapter cardsRepositoryAdapter) {
        return new UserServiceImpl(usersRepositoryAdapter, ordersRepositoryAdapter, cardsRepositoryAdapter);
    }
}
