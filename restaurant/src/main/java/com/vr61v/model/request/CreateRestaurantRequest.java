package com.vr61v.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public record CreateRestaurantRequest (

        @NotBlank
        String address,

        @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
        String phone,

        @NotNull
        LocalTime openingHoursFrom,

        @NotNull
        LocalTime openingHoursTo,

        @NotNull
        Set<UUID>menu

) {

}
