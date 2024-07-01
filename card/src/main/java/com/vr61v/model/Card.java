package com.vr61v.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "public", name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "number")
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CardType type;

    @Column(name = "discount")
    private Float discount;

}
