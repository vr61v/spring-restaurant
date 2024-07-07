package com.vr61v.ordermicroservice.model.order;

import com.vr61v.cardmicroservice.model.Card;
import com.vr61v.ordermicroservice.model.product.Detail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Field(value = "details")
    private Set<Detail> details;

    @Field(value = "card")
    private Card card;

    public void addDetail(Detail detail) {
        details.add(detail);
    }

    public void removeDetail(Detail detail) {
        details.remove(detail);
    }

}