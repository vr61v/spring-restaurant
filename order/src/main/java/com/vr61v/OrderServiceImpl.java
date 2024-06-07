package com.vr61v;

import com.vr61v.exceptions.IllegalOrderStateChangeException;
import com.vr61v.exceptions.NotEnoughMoneyException;
import com.vr61v.model.Order;
import com.vr61v.model.OrderState;
import com.vr61v.model.Product;
import com.vr61v.model.request.CreateOrderRequest;
import com.vr61v.model.request.UpdateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductService productService;

    private final OrderRepository orderRepository;

    private boolean validateOrderUpdate(Order order, UpdateOrderRequest updateOrderRequest) {
        boolean result = true;

        if (updateOrderRequest.userId() != null) result &= !Objects.equals(updateOrderRequest.userId(), order.getUserId());
        if (updateOrderRequest.restaurantId() != null) result &= !Objects.equals(updateOrderRequest.restaurantId(), order.getRestaurantId());
        if (updateOrderRequest.date() != null) result &= !Objects.equals(updateOrderRequest.date(), order.getDate());
        if (updateOrderRequest.comment() != null) result &= !Objects.equals(updateOrderRequest.comment(), order.getComment());

        return result;
    }

    @Override
    public Order createOrder(CreateOrderRequest createOrderRequest) {
        Order order = Order.builder()
                .userId(createOrderRequest.userId())
                .restaurantId(createOrderRequest.restaurantId())
                .date(createOrderRequest.date())
                .comment(createOrderRequest.comment())
                .state(createOrderRequest.state())
                .build();

        Map<UUID, Integer> productsIds = createOrderRequest.products();
        List<Product> products = productService.getProductsById(productsIds.keySet().stream().toList());
        for (Product product : products) {
            order.addProduct(product, productsIds.get(product.getId()));
        }

        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrder(UUID orderId, UpdateOrderRequest updateOrderRequest) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(IllegalArgumentException::new);

        if (!validateOrderUpdate(order, updateOrderRequest)) {
            throw new IllegalArgumentException("the updated fields must be different from the existing ones");
        }

        if (updateOrderRequest.userId() != null) order.setUserId(updateOrderRequest.userId());
        if (updateOrderRequest.restaurantId() != null) order.setRestaurantId(updateOrderRequest.restaurantId());
        if (updateOrderRequest.date() != null) order.setDate(updateOrderRequest.date());
        if (updateOrderRequest.comment() != null) order.setComment(updateOrderRequest.comment());

        if (updateOrderRequest.products() != null) {
            Map<UUID, Integer> productsIds = updateOrderRequest.products();
            List<Product> products = productService.getProductsById(productsIds.keySet().stream().toList());
            for (Product product : products) {
                order.addProduct(product, productsIds.get(product.getId()));
            }
        }

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderState(UUID orderId, OrderState state) throws IllegalOrderStateChangeException {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(IllegalArgumentException::new);

        if (order.getState().compareTo(state) < 0) {
            throw new IllegalOrderStateChangeException("the order status cannot be changed");
        }

        order.setState(state);

        return orderRepository.save(order);
    }

    @Override
    public Order payOrder(UUID orderId, Double amount) throws IllegalOrderStateChangeException, NotEnoughMoneyException {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(IllegalArgumentException::new);

        if (order.getState() != OrderState.CREATE) {
            throw  new IllegalOrderStateChangeException("order state must be CREATE");
        }

        double total = 0d;
        for (var product : order.getProducts().entrySet()) {
            total += product.getKey().getPrice() * product.getValue();
        }

        if (total > amount) {
            throw new NotEnoughMoneyException("you don't have enough money");
        }

        order.setState(OrderState.PAY);

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }
}
