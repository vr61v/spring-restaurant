package com.vr61v.model.request;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

public record UpdateOrderRequest(
        UUID userId,
        UUID restaurantId,
        Calendar date,
        String comment,
        Map<UUID, Integer>products
) {
}
