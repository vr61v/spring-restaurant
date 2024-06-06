package com.vr61v.model;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductDto {

    private UUID id;

    private String name;

    private Integer price;

    private Integer weight;

    private String category;

    private String composition;

    private String description;

}
