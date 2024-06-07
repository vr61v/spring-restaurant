package com.vr61v.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Document(collection = "restaurant")
public class Restaurant {

    @Id
    private UUID id;

    @Field(value = "address")
    private String address;

    @Field(value = "phone")
    private String phone;

    @Field(value = "openingHoursFrom")
    private LocalTime openingHoursFrom;

    @Field(value = "openingHoursTo")
    private LocalTime openingHoursTo;

    @Field(value = "menu")
    private Set<UUID> menu;

    public void addProductInMenu(UUID productId) {
        menu.add(productId);
    }

    public void removeProductFromMenu(UUID productId) {
        menu.remove(productId);
    }

}
