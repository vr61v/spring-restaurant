package com.vr61v;

import com.vr61v.model.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends MongoRepository<Order, UUID> {

}
