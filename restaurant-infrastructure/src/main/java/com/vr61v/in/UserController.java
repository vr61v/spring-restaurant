package com.vr61v.in;

import com.vr61v.exceptions.IllegalOrderStateChangeException;
import com.vr61v.exceptions.NotEnoughMoneyException;
import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.order.Order;
import com.vr61v.models.user.Card;
import com.vr61v.models.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.createUser(user);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user;

        try {
            user = userService.getUserById(id);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable UUID id,
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "phone") String phone,
            @RequestParam(required = false, value = "email") String email
    ) {
        User update;

        try {
            update = userService.updateUser(id, name, phone, email);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }


    @PostMapping("/{userId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable UUID userId, @RequestBody Order order) {
        order.setUserId(userId);
        Order created = userService.createOrder(order);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable UUID userId) {
        List<Order> orders = userService.getUserOrders(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID userId, @PathVariable UUID orderId) {
        Order order;

        try {
            order = userService.getOrderById(userId, orderId);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping("/{userId}/orders/{orderId}/pay")
    public ResponseEntity<Order> payOrder(@PathVariable UUID userId, @PathVariable UUID orderId, @RequestParam Integer amount) {
        Order order;

        try {
            order = userService.payOrder(userId, orderId, amount);
        } catch (NotEnoughMoneyException | IllegalOrderStateChangeException | NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping("/{userId}/orders/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable UUID userId, @PathVariable UUID orderId) {
        Order order;

        try {
            order = userService.cancelOrder(userId, orderId);
        } catch (IllegalOrderStateChangeException | NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @PostMapping("/{userId}/cards")
    public ResponseEntity<Card> registerCard(@PathVariable UUID userId, @RequestParam String cardNumber) {
        Card register;

        try {
            register = userService.registerCard(userId, cardNumber);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(register, HttpStatus.OK);
    }

    @GetMapping("/{userId}/cards")
    public ResponseEntity<List<Card>> getUserCards(@PathVariable UUID userId) {
        List<Card> cards = userService.getUserCards(userId);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/{userId}/cards/{number}")
    public ResponseEntity<Card> getCardByNumber(@PathVariable UUID userId, @PathVariable String number) {
        Card card;

        try {
            card = userService.getCardByNumber(userId, number);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(card, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}/cards/{cardId}")
    public ResponseEntity<UUID> deleteCard(@PathVariable UUID userId, @PathVariable UUID cardId) {
        try {
            userService.deleteCard(userId, cardId);
        } catch (NotFoundException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cardId, HttpStatus.OK);
    }
}
