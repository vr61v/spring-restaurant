package com.vr61v.out.entities.order.mapper;

import com.vr61v.models.order.Order;
import com.vr61v.out.entities.BaseMapper;
import com.vr61v.out.entities.order.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {OrderDetailMapper.class})
public interface OrderMapper extends BaseMapper<OrderEntity, Order> { }