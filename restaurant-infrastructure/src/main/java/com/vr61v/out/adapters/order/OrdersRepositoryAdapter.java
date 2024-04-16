package com.vr61v.out.adapters.order;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.order.Order;
import com.vr61v.models.order.types.OrderState;
import com.vr61v.out.entities.order.OrderEntity;
import com.vr61v.out.entities.order.mapper.OrderMapper;
import com.vr61v.out.order.Orders;
import com.vr61v.out.repositories.order.OrdersRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrdersRepositoryAdapter implements Orders {
    private final OrdersRepository repository;
    private final OrderMapper mapper;

    @Override
    public Order find(UUID id) throws NotFoundException {
        OrderEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Order with id {" + id + "} was not found."));
        return mapper.entityToModel(entity);
    }

    @Override
    public Order save(Order model) {
        OrderEntity saved = mapper.modelToEntity(model);
        repository.save(saved);
        return mapper.entityToModel(saved);
    }

    @Override
    public void delete(UUID id) throws NotFoundException {
        OrderEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Order with id {" + id + "} was not found."));
        repository.delete(entity);
    }

    @Override
    public List<Order> findByUserId(UUID id) {
        List<OrderEntity> orders = repository.findByUserId(id);
        return orders.stream().map(mapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    public List<Order> findByStateAndRestaurantId(OrderState state, UUID restaurantId) {
        List<OrderEntity> orders = repository.findByStateAndRestaurantId(state, restaurantId);
        return orders.stream().map(mapper::entityToModel).collect(Collectors.toList());
    }
}
