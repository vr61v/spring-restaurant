package com.vr61v;

import com.vr61v.exceptions.IllegalOrderStateChangeException;
import com.vr61v.exceptions.NotEnoughMoneyException;
import com.vr61v.model.order.Order;
import com.vr61v.model.order.OrderState;
import com.vr61v.model.request.CreateOrderRequest;
import com.vr61v.model.request.UpdateOrderRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(CreateOrderRequest createOrderRequest);

    Order getOrder(UUID orderId);

    List<Order> getOrders();

    Order updateOrder(UUID orderId, UpdateOrderRequest updateOrderRequest);

    Order updateOrderState(UUID orderId, OrderState state) throws IllegalOrderStateChangeException;

    Order payOrder(UUID orderId, Double amount) throws IllegalOrderStateChangeException, NotEnoughMoneyException;

    void deleteOrder(UUID orderId);

}
