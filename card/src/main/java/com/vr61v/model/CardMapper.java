package com.vr61v.model;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDto entityToDto(Card card);

    Card dtoToEntity(CardDto cardDto);

}
