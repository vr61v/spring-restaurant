package com.vr61v.cardmicroservice.model;

import lombok.Data;

import java.util.UUID;

@Data
public class CardDto {

    private UUID id;

    private UUID userId;

    private String number;

    private CardType type;

    private Float discount;

}
