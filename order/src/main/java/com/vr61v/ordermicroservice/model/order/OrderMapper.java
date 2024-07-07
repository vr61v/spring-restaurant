package com.vr61v.ordermicroservice.model.order;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto entityToDto(Order order);

    Order dtoToEntity(OrderDto orderDto);

}