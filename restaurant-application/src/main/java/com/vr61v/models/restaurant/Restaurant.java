package com.vr61v.models.restaurant;

import com.vr61v.models.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Restaurant {
    private UUID id;
    private String address;
    private String phone;
    private Time openingHoursFrom;
    private Time openingHoursTo;
    private List<Product> menu;

    public void addProductInMenu(Product product) {
        menu.add(product);
    }

    public void removeProductFromMenu(Product product) {
        menu.remove(product);
    }
}
