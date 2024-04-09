package com.vr61v.models.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class OrderDetail {
    private UUID id;
    private UUID orderId;
    private UUID productId;
    private Integer quantity;
}
