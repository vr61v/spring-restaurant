package com.vr61v.model.request;

import jakarta.validation.constraints.Pattern;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public record UpdateRestaurantRequest (

        String address,

        @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
        String phone,

        LocalTime openingHoursFrom,

        LocalTime openingHoursTo,

        Set<UUID> menu

) {

}
