package com.vr61v.model.request;

import com.vr61v.model.OrderState;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

public record CreateOrderRequest(
        UUID userId,
        UUID restaurantId,
        Calendar date,
        String comment,
        OrderState state,
        Map<UUID, Integer> products
) {

}
