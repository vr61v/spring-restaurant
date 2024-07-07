package com.vr61v.cardmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "card")
public class Card {
    
    @Id
    private UUID id;

    @Field(name = "user_id")
    private UUID userId;

    @Field(name = "number")
    private String number;

    @Field(name = "type")
    private CardType type;

    @Field(name = "discount")
    private Float discount;

}
