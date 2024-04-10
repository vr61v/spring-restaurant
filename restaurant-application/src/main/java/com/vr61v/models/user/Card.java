package com.vr61v.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class Card {
    private UUID id;
    private UUID userId;
    private String number;
    private CardType type;
    private Float discount;
}
