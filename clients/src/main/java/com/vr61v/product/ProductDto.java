package com.vr61v.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private UUID id;

    private String name;

    private Double price;

    private Double weight;

    private String category;

    private String composition;

    private String description;

}
