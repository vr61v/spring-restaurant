package com.vr61v;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface RestaurantRepository extends MongoRepository<Restaurant, UUID> {

}
