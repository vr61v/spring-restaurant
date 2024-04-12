package com.vr61v.out.adapters.order;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.order.OrderDetail;
import com.vr61v.out.entities.order.OrderDetailEntity;
import com.vr61v.out.entities.order.mapper.OrderDetailMapper;
import com.vr61v.out.order.OrderDetails;
import com.vr61v.out.repositories.order.OrderDetailsRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderDetailsAdapter implements OrderDetails {
    private final OrderDetailsRepository repository;
    private final OrderDetailMapper mapper;


    @Override
    public OrderDetail find(UUID id) throws NotFoundException {
        OrderDetailEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Order detail with id {" + id + "} was not found."));
        return mapper.entityToModel(entity);
    }

    @Override
    public OrderDetail save(OrderDetail model) {
        OrderDetailEntity saved = mapper.modelToEntity(model);
        repository.save(saved);
        return mapper.entityToModel(saved);
    }

    @Override
    public void delete(UUID id) throws NotFoundException {
        OrderDetailEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Order detail with id {" + id + "} was not found."));
        repository.delete(entity);
    }


    @Override
    public List<OrderDetail> findByOrderId(UUID id) {
        List<OrderDetailEntity> details = repository.findByOrderId(id);
        return details.stream().map(mapper::entityToModel).collect(Collectors.toList());
    }
}