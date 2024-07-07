package com.vr61v.ordermicroservice;

import com.vr61v.ordermicroservice.client.CardClient;
import com.vr61v.cardmicroservice.model.CardDto;
import com.vr61v.ordermicroservice.exceptions.IllegalOrderStateChangeException;
import com.vr61v.ordermicroservice.exceptions.NotEnoughMoneyException;
import com.vr61v.ordermicroservice.model.card.CardMapper;
import com.vr61v.ordermicroservice.model.order.Order;
import com.vr61v.ordermicroservice.model.order.OrderState;
import com.vr61v.ordermicroservice.model.product.Detail;
import com.vr61v.ordermicroservice.model.product.ProductMapper;
import com.vr61v.ordermicroservice.model.request.CreateOrderRequest;
import com.vr61v.ordermicroservice.model.request.UpdateOrderRequest;
import com.vr61v.ordermicroservice.client.ProductClient;
import com.vr61v.productmicroservice.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final ProductMapper productMapper;
    private final CardClient cardClient;
    private final CardMapper cardMapper;

    @Override
    @PreAuthorize("hasRole(\"CUSTOMER\")")
    public Order createOrder(CreateOrderRequest createOrderRequest) {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .userId(createOrderRequest.userId())
                .restaurantId(createOrderRequest.restaurantId())
                .date(createOrderRequest.date())
                .comment(createOrderRequest.comment())
                .state(createOrderRequest.state())
                .details(new HashSet<>())
                .build();


        Map<UUID, Integer> details = createOrderRequest.details();
        List<Product> products = Objects.requireNonNull(
                productClient.getProductsById(details.keySet().stream().toList()).getBody())
                .stream().map(productMapper::dtoToEntity).toList();
        for (Product product : products) {
            order.addDetail(new Detail(product, details.get(product.getId())));
        }

        ResponseEntity<?> card = cardClient.getCard(null, createOrderRequest.cardNumber());
        if (card.getStatusCode().is2xxSuccessful()) {
            order.setCard(cardMapper.dtoToEntity((CardDto) card.getBody()));
        } else {
            throw new IllegalArgumentException((String) card.getBody());
        }

        return orderRepository.save(order);
    }

    @Override
    @PostAuthorize("hasAnyRole(\"ADMIN\", \"COOK\") ||" +
            "hasRole(\"CUSTOMER\") && returnObject.userId == authentication.principal.getClaims().get(\"sub\")")
    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    @PostFilter("hasAnyRole(\"ADMIN\", \"COOK\") ||" +
            "hasRole(\"CUSTOMER\") && filterObject.userId == authentication.principal.getClaims().get(\"sub\")")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @PreAuthorize("hasAnyRole(\"ADMIN\")")
    public Order updateOrder(UUID orderId, UpdateOrderRequest updateOrderRequest) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(IllegalArgumentException::new);

        if (updateOrderRequest.userId() != null) order.setUserId(updateOrderRequest.userId());
        if (updateOrderRequest.restaurantId() != null) order.setRestaurantId(updateOrderRequest.restaurantId());
        if (updateOrderRequest.date() != null) order.setDate(updateOrderRequest.date());
        if (updateOrderRequest.comment() != null) order.setComment(updateOrderRequest.comment());

        if (updateOrderRequest.details() != null) {
            Map<UUID, Integer> details = updateOrderRequest.details();

            List<Product> products = Objects.requireNonNull(
                    productClient.getProductsById(details.keySet().stream().toList()).getBody())
                    .stream().map(productMapper::dtoToEntity).toList();

            order.getDetails().clear();
            for (Product product : products) {
                order.addDetail(new Detail(product, details.get(product.getId())));
            }
        }

        return orderRepository.save(order);
    }

    @Override
    @PreAuthorize("hasAnyRole(\"ADMIN\", \"COOK\")")
    public Order updateOrderState(UUID orderId, OrderState state) throws IllegalOrderStateChangeException {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(IllegalArgumentException::new);

        if (order.getState().compareTo(state) > 0) {
            throw new IllegalOrderStateChangeException("the order status cannot be changed");
        }

        order.setState(state);

        return orderRepository.save(order);
    }

    @Override
    @PreAuthorize("hasRole(\"CUSTOMER\")")
    public Order payOrder(UUID orderId, Double amount) throws IllegalOrderStateChangeException, NotEnoughMoneyException {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(IllegalArgumentException::new);

        if (order.getState() != OrderState.CREATE) {
            throw  new IllegalOrderStateChangeException("order state must be CREATE");
        }

        double total = 0d;
        for (var detail : order.getDetails()) {
            total += detail.product().getPrice() * detail.quantity();
        }

        if (total > amount) {
            throw new NotEnoughMoneyException("you don't have enough money");
        }

        order.setState(OrderState.PAY);

        return orderRepository.save(order);
    }

    @Override
    @PreAuthorize("hasRole(\"ADMIN\")")
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }
}
