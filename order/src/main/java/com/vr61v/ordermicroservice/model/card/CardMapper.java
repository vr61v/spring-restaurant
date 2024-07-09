package com.vr61v.ordermicroservice.model.card;

import com.vr61v.cardmicroservice.model.Card;
import com.vr61v.cardmicroservice.model.CardDto;
import org.mapstruct.Mapper;

import java.util.HashMap;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDto entityToDto(Card card);

    Card dtoToEntity(CardDto cardDto);

}
