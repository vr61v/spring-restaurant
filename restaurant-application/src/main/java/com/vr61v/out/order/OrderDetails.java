package com.vr61v.out.order;

import com.vr61v.models.order.OrderDetail;
import com.vr61v.out.BasicRepository;

import java.util.List;
import java.util.UUID;

public interface OrderDetails extends BasicRepository<OrderDetail> {
    List<OrderDetail> findByOrderId(UUID id);
}