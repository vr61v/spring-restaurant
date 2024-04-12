package com.vr61v.out.repositories.order;

import com.vr61v.models.order.types.OrderState;
import com.vr61v.out.entities.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrdersRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findByUserId(UUID id);
    List<OrderEntity> findByState(OrderState state);
}
