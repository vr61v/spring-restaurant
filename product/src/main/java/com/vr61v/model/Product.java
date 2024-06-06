package com.vr61v.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Data
@Builder
@Document(collection = "product")
public class Product {

    @Id
    private UUID id;

    @Field(value = "name")
    private String name;

    @Field(value = "price")
    private Integer price;

    @Field(value = "weight")
    private Integer weight;

    @Field(value = "category")
    private String category;

    @Field(value = "composition")
    private String composition;

    @Field(value = "description")
    private String description;

}
