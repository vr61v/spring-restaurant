package com.vr61v.in;

import com.vr61v.exceptions.IllegalOrderStateChangeException;
import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cook")
@RequiredArgsConstructor
@Slf4j
public class CookController {
    private final CookService cookService;

    @GetMapping("/orders/awaiting")
    public ResponseEntity<List<Order>> getAwaitingOrders(@RequestParam UUID restaurantId) {
        List<Order> orders = cookService.getAwaitingOrders(restaurantId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders/active")
    public ResponseEntity<List<Order>> getActiveOrders(@RequestParam UUID restaurantId) {
        List<Order> orders = cookService.getActiveOrders(restaurantId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PatchMapping("/orders/{orderId}/take")
    public ResponseEntity<Order> takeOrder(@PathVariable UUID orderId) {
        Order order;

        try {
            order = cookService.takeOrder(orderId);
        } catch (IllegalOrderStateChangeException | NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping("/orders/{orderId}/complete")
    public ResponseEntity<Order> completeOrder(@PathVariable UUID orderId) {
        Order order;

        try {
            order = cookService.completeOrder(orderId);
        } catch (IllegalOrderStateChangeException | NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PatchMapping("/orders/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable UUID orderId) {
        Order order;

        try {
            order = cookService.cancelOrder(orderId);
        } catch (IllegalOrderStateChangeException | NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
