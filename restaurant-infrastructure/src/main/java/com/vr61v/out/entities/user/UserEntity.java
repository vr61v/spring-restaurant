package com.vr61v.out.entities.user;

import com.vr61v.models.user.types.Gender;
import com.vr61v.models.user.types.Role;
import com.vr61v.out.entities.order.OrderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "public", name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_birth")
    private Calendar dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private Set<OrderEntity> orders;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private Set<CardEntity> cards;
}
