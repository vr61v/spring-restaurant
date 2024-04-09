package com.vr61v.in;

import com.vr61v.models.order.Order;
import com.vr61v.models.order.OrderDetail;
import com.vr61v.models.user.Card;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public interface UserService extends BasicUserService{
    Order createOrder(UUID userId, UUID restaurantId, Calendar date, String comment, String address, List<OrderDetail> details);
    Order getOrderById(UUID orderId);
    List<Order> getUserOrders(UUID userId);
    Order payOrder(UUID orderId, Float amount);
    Order cancelOrder(UUID orderId);

    Card registerCard(String cardNumber);
    Card getCardByNumber(String cardNumber);
    List<Card> getUserCards(UUID userId);
    UUID deleteCard(String cardNumber);
}