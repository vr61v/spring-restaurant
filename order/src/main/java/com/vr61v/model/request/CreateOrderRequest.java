package com.vr61v.model.request;

import com.vr61v.model.Detail;
import com.vr61v.model.OrderState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Set;
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
        Set<Detail> details

) {

}
