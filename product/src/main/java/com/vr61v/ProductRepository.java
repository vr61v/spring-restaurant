package com.vr61v;

import com.vr61v.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends MongoRepository<Product, UUID> {

}
