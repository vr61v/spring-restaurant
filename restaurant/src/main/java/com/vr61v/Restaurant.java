package com.vr61v;

import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class Restaurant {

    private UUID id;

    private String address;

    private String phone;

    private Time openingHoursFrom;

    private Time openingHoursTo;

    private Set<UUID> menu;

    public void addProductInMenu(UUID productId) {
        menu.add(productId);
    }

    public void removeProductFromMenu(UUID productId) {
        menu.remove(productId);
    }

}
