package com.vr61v.models.order;

import com.vr61v.models.order.types.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Order {
    private UUID id;
    private UUID userId;
    private UUID restaurantId;
    private Calendar date;
    private String comment;
    private OrderState state;
    private List<OrderDetail> details;

    public void addDetail(OrderDetail detail) {
        details.add(detail);
        detail.setOrderId(this.id);
    }

    public void removeDetail(OrderDetail detail) {
        details.remove(detail);
        detail.setOrderId(null);
    }
}