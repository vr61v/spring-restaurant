package com.vr61v.restaurantmicroservice.model;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDto entityToDto(Restaurant restaurant);

    Restaurant dtoToEntity(RestaurantDto restaurantDto);

}
