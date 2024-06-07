package com.vr61v;

import com.vr61v.model.Card;
import com.vr61v.model.request.CreateCardRequest;
import com.vr61v.model.request.UpdateCardRequest;

import java.util.List;
import java.util.UUID;

public interface CardService {

    Card createCard(CreateCardRequest createCardRequest);

    Card getCardById(UUID cardId);

    List<Card> getAllCards();

    Card updateCard(UUID cardId, UpdateCardRequest updateCardRequest);

    void deleteCard(UUID cardId);

}
