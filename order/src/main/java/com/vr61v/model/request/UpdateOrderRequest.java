package com.vr61v.model.request;

import com.vr61v.model.Detail;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public record UpdateOrderRequest(

        UUID userId,

        UUID restaurantId,

        Date date,

        String comment,

        Set<Detail> details

) {
}
