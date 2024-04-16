package com.vr61v.in;

import com.vr61v.out.adapters.order.OrdersRepositoryAdapter;
import com.vr61v.services.CookServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CookControllerConfiguration {
    @Bean
    public CookService cookService(OrdersRepositoryAdapter ordersRepositoryAdapter) {
        return new CookServiceImpl(ordersRepositoryAdapter);
    }
}
