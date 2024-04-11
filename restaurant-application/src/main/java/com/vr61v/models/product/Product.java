package com.vr61v.models.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class Product {
    private UUID id;
    private ProductCategory productCategory;
    private String name;
    private Integer price;
    private Integer weight;
    private String composition;
    private String description;
}
