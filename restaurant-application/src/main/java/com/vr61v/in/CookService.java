package com.vr61v.in;

import com.vr61v.exceptions.IllegalOrderStateChangeException;
import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.order.Order;

import java.util.List;
import java.util.UUID;

public interface CookService {
    List<Order> getAwaitingOrders(UUID restaurantId);
    List<Order> getActiveOrders(UUID restaurantId);
    Order takeOrder(UUID orderId) throws IllegalOrderStateChangeException, NotFoundException;
    Order completeOrder(UUID orderId) throws IllegalOrderStateChangeException, NotFoundException;
    Order cancelOrder(UUID orderId) throws IllegalOrderStateChangeException, NotFoundException;
}