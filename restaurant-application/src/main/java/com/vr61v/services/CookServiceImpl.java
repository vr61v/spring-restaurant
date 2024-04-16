package com.vr61v.services;

import com.vr61v.exceptions.IllegalOrderStateChangeException;
import com.vr61v.exceptions.NotFoundException;
import com.vr61v.in.CookService;
import com.vr61v.models.order.Order;
import com.vr61v.models.order.types.OrderState;
import com.vr61v.out.order.Orders;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CookServiceImpl implements CookService {
    private final Orders orders;

    @Override
    public List<Order> getAwaitingOrders(UUID restaurantId) {
        return orders.findByStateAndRestaurantId(OrderState.PAY, restaurantId);
    }

    @Override
    public List<Order> getActiveOrders(UUID restaurantId) {
        return orders.findByStateAndRestaurantId(OrderState.COOK, restaurantId);
    }

    @Override
    public Order takeOrder(UUID orderId) throws IllegalOrderStateChangeException, NotFoundException {
        Order order = orders.find(orderId);
        if (!order.getState().equals(OrderState.PAY)) {
            throw new IllegalOrderStateChangeException("The order must be in PAY state");
        }

        order.setState(OrderState.COOK);
        orders.save(order);

        return order;
    }

    @Override
    public Order completeOrder(UUID orderId) throws IllegalOrderStateChangeException, NotFoundException {
        Order order = orders.find(orderId);
        if (!order.getState().equals(OrderState.COOK)) {
            throw new IllegalOrderStateChangeException("The order must be in COOK state");
        }

        order.setState(OrderState.DONE);
        orders.save(order);

        return order;
    }

    @Override
    public Order cancelOrder(UUID orderId) throws IllegalOrderStateChangeException, NotFoundException {
        Order order = orders.find(orderId);
        if (!order.getState().equals(OrderState.COOK)) {
            throw new IllegalOrderStateChangeException("The order must be in COOK state");
        }

        order.setState(OrderState.CANCEL);
        orders.save(order);

        return order;
    }
}
