package com.vr61v.in;

import com.vr61v.models.order.Order;

import java.util.List;
import java.util.UUID;

public interface CookService {
    List<Order> getActiveOrders(UUID restaurantId);
    Order takeOrder(UUID orderId);
    Order completeOrder(UUID orderID);
    Order cancelOrder(UUID orderId);
}