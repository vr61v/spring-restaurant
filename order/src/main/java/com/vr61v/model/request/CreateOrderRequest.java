package com.vr61v.model.request;

import com.vr61v.model.OrderState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

public record CreateOrderRequest(

        @NotNull
        UUID userId,

        @NotNull
        UUID restaurantId,

        @NotNull
        Calendar date,

        @NotBlank
        String comment,

        OrderState state,

        @NotNull @NotEmpty
        Map<UUID, Integer> products

) {

}
