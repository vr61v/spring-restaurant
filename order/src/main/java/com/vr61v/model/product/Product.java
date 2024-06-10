package com.vr61v.model.product;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Product {

    private UUID id;

    private String name;

    private Double price;

    private Double weight;

    private String category;

    private String composition;

    private String description;

}
