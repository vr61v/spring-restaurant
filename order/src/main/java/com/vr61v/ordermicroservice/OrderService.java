package com.vr61v.ordermicroservice;

import com.vr61v.ordermicroservice.exceptions.IllegalOrderStateChangeException;
import com.vr61v.ordermicroservice.exceptions.NotEnoughMoneyException;
import com.vr61v.ordermicroservice.model.order.Order;
import com.vr61v.ordermicroservice.model.order.OrderState;
import com.vr61v.ordermicroservice.model.request.CreateOrderRequest;
import com.vr61v.ordermicroservice.model.request.UpdateOrderRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(CreateOrderRequest createOrderRequest);

    Order getOrderById(UUID orderId);

    List<Order> getAllOrders();

    Order updateOrder(UUID orderId, UpdateOrderRequest updateOrderRequest);

    Order updateOrderState(UUID orderId, OrderState state) throws IllegalOrderStateChangeException;

    Order payOrder(UUID orderId, Double amount) throws IllegalOrderStateChangeException, NotEnoughMoneyException;

    void deleteOrder(UUID orderId);

}
