package com.vr61v.in;

import com.vr61v.exceptions.IllegalOrderStateChangeException;
import com.vr61v.exceptions.NotEnoughMoneyException;
import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.order.Order;
import com.vr61v.models.user.Card;

import java.util.List;
import java.util.UUID;

public interface UserService extends BasicUserService{
    Order createOrder(Order order);
    Order getOrderById(UUID userId, UUID orderId) throws NotFoundException;
    List<Order> getUserOrders(UUID userId);
    Order payOrder(UUID userId, UUID orderId, Integer amount) throws NotEnoughMoneyException, IllegalOrderStateChangeException, NotFoundException;
    Order cancelOrder(UUID userId, UUID orderId) throws IllegalOrderStateChangeException, NotFoundException;

    Card registerCard(UUID userId, String cardNumber) throws NotFoundException;
    Card getCardByNumber(UUID userId, String cardNumber) throws NotFoundException;
    List<Card> getUserCards(UUID userId);
    UUID deleteCard(UUID userId, UUID cardId) throws NotFoundException;
}