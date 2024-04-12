package com.vr61v.out.entities.restaurant.mappers;

import com.vr61v.models.restaurant.Restaurant;
import com.vr61v.out.entities.BaseMapper;
import com.vr61v.out.entities.order.mapper.OrderMapper;
import com.vr61v.out.entities.product.mappers.ProductMapper;
import com.vr61v.out.entities.restaurant.RestaurantEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, OrderMapper.class})
public interface RestaurantMapper extends BaseMapper<RestaurantEntity, Restaurant> { }