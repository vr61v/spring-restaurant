package com.vr61v.cardmicroservice;

import com.vr61v.cardmicroservice.model.Card;
import com.vr61v.cardmicroservice.model.request.CreateCardRequest;
import com.vr61v.cardmicroservice.model.request.UpdateCardRequest;

import java.util.List;
import java.util.UUID;

public interface CardService {

    Card createCard(CreateCardRequest createCardRequest);

    Card getCardById(UUID cardId);

    Card getCardByNumber(String number);

    List<Card> getAllCards();

    Card updateCard(UUID cardId, UpdateCardRequest updateCardRequest);

    void deleteCardById(UUID cardId);

    void deleteCardByNumber(String number);

}
