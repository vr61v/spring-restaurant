package com.vr61v.model.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

public record UpdateOrderRequest(

        UUID userId,

        UUID restaurantId,

        Calendar date,

        @NotBlank
        String comment,

        Map<UUID, Integer>products

) {
}
