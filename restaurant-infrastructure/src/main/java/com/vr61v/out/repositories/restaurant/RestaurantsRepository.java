package com.vr61v.out.repositories.restaurant;

import com.vr61v.out.entities.restaurant.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantsRepository extends JpaRepository<RestaurantEntity, UUID> { }
