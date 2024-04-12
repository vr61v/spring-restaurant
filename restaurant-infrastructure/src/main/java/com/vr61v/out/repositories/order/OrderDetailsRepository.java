package com.vr61v.out.repositories.order;

import com.vr61v.out.entities.order.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderDetailsRepository extends JpaRepository<OrderDetailEntity, UUID> {
    List<OrderDetailEntity> findByOrderId(UUID id);
}