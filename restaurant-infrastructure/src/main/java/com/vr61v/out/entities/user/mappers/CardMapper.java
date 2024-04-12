package com.vr61v.out.entities.user.mappers;

import com.vr61v.models.user.Card;
import com.vr61v.out.entities.BaseMapper;
import com.vr61v.out.entities.user.CardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper extends BaseMapper<CardEntity, Card> { }