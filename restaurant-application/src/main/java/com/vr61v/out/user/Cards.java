package com.vr61v.out.user;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.user.Card;
import com.vr61v.out.BasicRepository;

import java.util.List;
import java.util.UUID;

public interface Cards extends BasicRepository<Card> {
    Card findByNumber(String number) throws NotFoundException;
    List<Card> findByUserId(UUID id);
}
