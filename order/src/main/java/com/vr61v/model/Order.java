package com.vr61v.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@Document(collection = "order")
public class Order {

    @Id
    private UUID id;

    @Field(value = "user_id")
    private UUID userId;

    @Field(value = "restaurant_id")
    private UUID restaurantId;

    @Field(value = "date")
    private Date date;

    @Field(value = "comment")
    private String comment;

    @Field(value = "state")
    private OrderState state;

    @Field(value = "products")
    private Map<Product, Integer> products;

    public void addProduct(Product product, Integer quantity) {
        products.put(product, products.getOrDefault(product, 0) + quantity);
    }

    public void removeDetail(Product product) {
        products.remove(product);
    }

}