package com.vr61v.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
