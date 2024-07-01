package com.vr61v.model.request;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public record UpdateOrderRequest(

        UUID userId,

        UUID restaurantId,

        Date date,

        String comment,

        Map<UUID, Integer> details

) {
}
