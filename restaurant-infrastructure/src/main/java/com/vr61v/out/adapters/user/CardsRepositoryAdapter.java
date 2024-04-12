package com.vr61v.out.adapters.user;

import com.vr61v.exceptions.NotFoundException;
import com.vr61v.models.user.Card;
import com.vr61v.out.entities.user.CardEntity;
import com.vr61v.out.entities.user.mappers.CardMapper;
import com.vr61v.out.repositories.user.CardsRepository;
import com.vr61v.out.user.Cards;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CardsRepositoryAdapter implements Cards {
    private final CardsRepository repository;
    private final CardMapper mapper;

    @Override
    public Card find(UUID id) throws NotFoundException {
        CardEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Card with id {" + id + "} was not found."));
        return mapper.entityToModel(entity);
    }

    @Override
    public Card save(Card model) {
        CardEntity saved = mapper.modelToEntity(model);
        repository.save(saved);
        return mapper.entityToModel(saved);
    }

    @Override
    public void delete(UUID id) throws NotFoundException {
        CardEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Card with id {" + id + "} was not found."));
        repository.delete(entity);
    }

    @Override
    public Card findByNumber(String number) throws NotFoundException {
        CardEntity card = repository
                .findByNumber(number)
                .orElseThrow(() -> new NotFoundException("Card with number {" + number + "} was not found."));
        return mapper.entityToModel(card);
    }

    @Override
    public List<Card> findByUserId(UUID id) {
        List<CardEntity> card = repository.findByUserId(id);
        return card.stream().map(mapper::entityToModel).collect(Collectors.toList());
    }
}
