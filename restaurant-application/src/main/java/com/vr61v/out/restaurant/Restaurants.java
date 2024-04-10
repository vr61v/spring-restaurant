package com.vr61v.out.restaurant;

import com.vr61v.models.restaurant.Restaurant;
import com.vr61v.out.BasicRepository;

import java.util.List;

public interface Restaurants extends BasicRepository<Restaurant> {
    List<Restaurant> findAll();
}
