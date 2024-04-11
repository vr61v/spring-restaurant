package com.vr61v.out.entities.order;

import com.vr61v.out.entities.order.types.OrderState;
import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "public", name = "order")
public class OrderEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "restaurant_id")
    private UUID restaurantId;

    @Column(name = "date")
    private Calendar date;

    @Column(name = "comment")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrderState state;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderDetailEntity> details;

    public void addDetail(OrderDetailEntity detail) {
        details.add(detail);
        detail.setOrderId(this.id);
    }

    public void removeDetail(OrderDetailEntity detail) {
        details.remove(detail);
        detail.setOrderId(null);
    }
}