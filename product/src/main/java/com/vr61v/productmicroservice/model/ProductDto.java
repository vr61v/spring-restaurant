package com.vr61v.productmicroservice.model;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductDto {

    private UUID id;

    private String name;

    private Double price;

    private Double weight;

    private String category;

    private String composition;

    private String description;

}
