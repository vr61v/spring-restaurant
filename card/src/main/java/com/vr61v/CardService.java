package com.vr61v;

import java.util.List;
import java.util.UUID;

public interface CardService {

    Card createCard(Card card);

    Card getCardById(UUID cardId);

    List<Card> getAllCards();

    Card updateCard(UUID cardId, UUID userId, String number, CardType type, Float discount);

    void deleteCard(UUID cardId);

}
