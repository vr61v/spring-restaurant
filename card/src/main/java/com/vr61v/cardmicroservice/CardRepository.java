package com.vr61v.cardmicroservice;

import com.vr61v.cardmicroservice.model.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends MongoRepository<Card, UUID> {

    Optional<Card> findByNumber(String number);

    void deleteByNumber(String number);

}
