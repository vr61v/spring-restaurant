package com.vr61v.ordermicroservice.model.request;

import com.vr61v.ordermicroservice.model.order.OrderState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public record CreateOrderRequest(

        @NotNull
        UUID userId,

        @NotNull
        UUID restaurantId,

        @NotNull
        Date date,

        @NotBlank
        String comment,

        OrderState state,

        @NotNull @NotEmpty
        Map<UUID, Integer> details,

        String cardNumber

) {

}
