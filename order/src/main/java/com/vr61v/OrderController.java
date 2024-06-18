package com.vr61v;

import com.vr61v.model.order.Order;
import com.vr61v.model.order.OrderDto;
import com.vr61v.model.order.OrderMapper;
import com.vr61v.model.order.OrderState;
import com.vr61v.model.request.CreateOrderRequest;
import com.vr61v.model.request.UpdateOrderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        Order order;
        try {
            order = orderService.createOrder(createOrderRequest);
        } catch (Exception e) {
            log.error("Error create order {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Created order: {}", order);
        return new ResponseEntity<>(orderMapper.entityToDto(order), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") UUID id) {
        Order order = orderService.getOrder(id);
        log.info("Retrieved order: {}", order);
        if (order == null) return new ResponseEntity<>("The order with id:" + id + " was not found", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(orderMapper.entityToDto(order), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<Order> orders = orderService.getOrders();
        log.info("Retrieved orders: {}", orders);
        return new ResponseEntity<>(
                orders.stream()
                        .map(orderMapper::entityToDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateOrderRequest updateOrderRequest) {
        Order order;
        try {
            order = orderService.updateOrder(id, updateOrderRequest);
        } catch (Exception e) {
            log.error("Error update order: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Updated order: {}", order);
        return new ResponseEntity<>(orderMapper.entityToDto(order), HttpStatus.OK);
    }

    @PatchMapping("/{id}/state")
    public ResponseEntity<?> updateOrderState(@PathVariable("id") UUID id, @RequestParam(name = "orderState") OrderState orderState) {
        Order order;
        try {
            order = orderService.updateOrderState(id, orderState);
        } catch (Exception e) {
            log.error("Error update order state: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Updated order state: {}", order);
        return new ResponseEntity<>(orderMapper.entityToDto(order), HttpStatus.OK);
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<?> payOrder(@PathVariable("id") UUID id, @RequestParam(name = "amount") Double amount) {
        Order order;
        try {
            order = orderService.payOrder(id, amount);
        } catch (Exception e) {
            log.error("Error pay order: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Payed order: {}", order);
        return new ResponseEntity<>(orderMapper.entityToDto(order), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") UUID id) {
        try {
            orderService.deleteOrder(id);
        } catch (Exception e) {
            log.error("Error delete order: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Deleted order: {}", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
