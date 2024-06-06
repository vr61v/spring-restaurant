package com.vr61v;

import com.vr61v.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface RestaurantRepository extends MongoRepository<Restaurant, UUID> {

}
