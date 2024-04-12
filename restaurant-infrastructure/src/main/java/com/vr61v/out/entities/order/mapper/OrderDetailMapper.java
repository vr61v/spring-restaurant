package com.vr61v.out.entities.order.mapper;

import com.vr61v.models.order.OrderDetail;
import com.vr61v.out.entities.BaseMapper;
import com.vr61v.out.entities.order.OrderDetailEntity;
import com.vr61v.out.entities.product.mappers.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderDetailMapper extends BaseMapper<OrderDetailEntity, OrderDetail> { }