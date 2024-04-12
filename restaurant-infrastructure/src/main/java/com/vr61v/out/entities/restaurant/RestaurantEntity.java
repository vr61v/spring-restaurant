package com.vr61v.out.entities.restaurant;

import com.vr61v.out.entities.order.OrderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.vr61v.out.entities.product.ProductEntity;

import java.sql.Time;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "public", name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "opening_hours_from")
    private Time openingHoursFrom;

    @Column(name = "opening_hours_to")
    private Time openingHoursTo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "menu",
            joinColumns = @JoinColumn(name = "restaurant", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product", referencedColumnName = "id")
    )
    private Set<ProductEntity> menu;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "restaurant_id")
//    private List<OrderEntity> orders;

    public void addProductInMenu(ProductEntity product) {
        menu.add(product);
    }

    public void removeProductFromMenu(ProductEntity product) {
        menu.remove(product);
    }
}
