package com.vr61v.services;

import com.vr61v.exceptions.IllegalOrderStateChangeException;
import com.vr61v.exceptions.NotEnoughMoneyException;
import com.vr61v.exceptions.NotFoundException;
import com.vr61v.in.UserService;
import com.vr61v.models.order.Order;
import com.vr61v.models.order.OrderDetail;
import com.vr61v.models.order.types.OrderState;
import com.vr61v.models.user.Card;
import com.vr61v.models.user.types.Role;
import com.vr61v.models.user.User;
import com.vr61v.out.order.Orders;
import com.vr61v.out.user.Cards;
import com.vr61v.out.user.Users;
import lombok.RequiredArgsConstructor;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Users users;
    private final Orders orders;
    private final Cards cards;

    @Override
    public User createUser(User user) {
        user.setRole(Role.USER);
        return users.save(user);
    }

    @Override
    public User getUserById(UUID userId) throws NotFoundException {
        User find = users.find(userId);
        if (find.getRole() != Role.USER) {
            throw new IllegalArgumentException("User can get only USER");
        }
        return find;
    }

    @Override
    public User updateUser(UUID userId, String name, String phone, String email, String password) throws NotFoundException {
        User update = users.find(userId);
        if (update.getRole() != Role.USER) {
            throw new IllegalArgumentException("User can update only USER");
        }

        if (!UpdateEntityValidator.validateUserUpdate(update, name, phone, email, password)) {
            throw new IllegalArgumentException("The updated fields must be different from the existing ones.");
        }

        if (name != null) update.setName(name);
        if (phone != null) update.setPhone(phone);
        if (email != null) update.setEmail(email);
        if (password != null) update.setPassword(password);

        return update;
    }

    @Override
    public UUID deleteUser(UUID userId) throws NotFoundException {
        if (users.find(userId).getRole() != Role.USER) {
            throw new IllegalArgumentException("User can delete only USER");
        }
        users.delete(userId);
        return userId;
    }

    @Override
    public Order createOrder(UUID userId, UUID restaurantId, Calendar date, String comment, String address, List<OrderDetail> details) {
        Order order = Order
                .builder()
                .userId(userId)
                .restaurantId(restaurantId)
                .date(date)
                .comment(comment)
                .details(details)
                .build();
        return orders.save(order);
    }

    @Override
    public Order getOrderById(UUID orderId) throws NotFoundException {
        return orders.find(orderId);
    }

    @Override
    public List<Order> getUserOrders(UUID userId) {
        return orders.findByUserId(userId);
    }

    @Override
    public Order payOrder(UUID orderId, Integer amount) throws NotEnoughMoneyException, IllegalOrderStateChangeException, NotFoundException {
        Order order = orders.find(orderId);
        if (order.getState() != OrderState.CREATE) {
            throw new IllegalOrderStateChangeException("The order has already been paid for.");
        }

        int totalPrice = 0;
        for (OrderDetail i : order.getDetails()) {
            totalPrice += i.getQuantity() * i.getProduct().getPrice();
        }

        if (totalPrice <= amount) {
            order.setState(OrderState.PAY);
            orders.save(order);
        } else {
            throw new NotEnoughMoneyException(
                    "The order" + order.getId() + " has total price {" + totalPrice +
                    "}, amount {" + amount + "} is not enough.");
        }

        return order;
    }

    @Override
    public Order cancelOrder(UUID orderId) throws IllegalOrderStateChangeException, NotFoundException {
        Order order = orders.find(orderId);

        if (order.getState() != OrderState.CREATE && order.getState() != OrderState.PAY) {
            throw new IllegalOrderStateChangeException("The order must be in CREATE or PAY state.");
        }

        order.setState(OrderState.CANCEL);
        orders.save(order);

        return order;
    }

    @Override
    public Card registerCard(UUID userId, String cardNumber) throws NotFoundException {
        Card card = cards.findByNumber(cardNumber);

        card.setUserId(userId);
        cards.save(card);

        return card;
    }

    @Override
    public Card getCardById(UUID cardId) throws NotFoundException {
        return cards.find(cardId);
    }

    @Override
    public List<Card> getUserCards(UUID userId) {
        return cards.findByUserId(userId);
    }

    @Override
    public UUID deleteCard(UUID cardId) throws NotFoundException {
        cards.delete(cardId);
        return cardId;
    }
}
