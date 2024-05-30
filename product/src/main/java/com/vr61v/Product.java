package com.vr61v;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Product {

    private UUID id;

    private String name;

    private Integer price;

    private Integer weight;

    private String category;

    private String composition;

    private String description;

}
