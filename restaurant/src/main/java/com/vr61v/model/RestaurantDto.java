package com.vr61v.model;

import lombok.Data;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Data
public class RestaurantDto {

    private UUID id;

    private String address;

    private String phone;

    private LocalTime openingHoursFrom;

    private LocalTime openingHoursTo;

    private Set<UUID> menu;

}
