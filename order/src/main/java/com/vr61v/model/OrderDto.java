package com.vr61v.model;

import lombok.Data;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

@Data
public class OrderDto {

    private UUID id;

    private UUID userId;

    private UUID restaurantId;

    private Calendar date;

    private String comment;

    private OrderState state;

    private Map<UUID, Integer> details;

}
