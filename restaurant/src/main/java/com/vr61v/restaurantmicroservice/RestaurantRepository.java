package com.vr61v.restaurantmicroservice;

import com.vr61v.restaurantmicroservice.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, UUID> {

}
