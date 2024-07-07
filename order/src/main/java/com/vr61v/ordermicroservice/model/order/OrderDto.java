package com.vr61v.ordermicroservice.model.order;

import com.vr61v.cardmicroservice.model.Card;
import com.vr61v.ordermicroservice.model.product.Detail;
import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderDto {

    private UUID id;

    private UUID userId;

    private UUID restaurantId;

    private Date date;

    private String comment;

    private OrderState state;

    private Set<Detail> details;

    private Card card;

}
