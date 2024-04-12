package com.vr61v.out.repositories.user;

import com.vr61v.out.entities.user.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardsRepository extends JpaRepository<CardEntity, UUID> {
    Optional<CardEntity> findByNumber(String number);
    List<CardEntity> findByUserId(UUID id);
}
