package com.vr61v.productmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product {

    @Id
    private UUID id;

    @Field(value = "name")
    private String name;

    @Field(value = "price")
    private Double price;

    @Field(value = "weight")
    private Double weight;

    @Field(value = "category")
    private String category;

    @Field(value = "composition")
    private String composition;

    @Field(value = "description")
    private String description;

}
