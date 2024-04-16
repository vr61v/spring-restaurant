package com.vr61v.out.order;

import com.vr61v.models.order.Order;
import com.vr61v.models.order.types.OrderState;
import com.vr61v.out.BasicRepository;

import java.util.List;
import java.util.UUID;

public interface Orders extends BasicRepository<Order> {
    List<Order> findByUserId(UUID id);
    List<Order> findByStateAndRestaurantId(OrderState state, UUID restaurantId);
}
