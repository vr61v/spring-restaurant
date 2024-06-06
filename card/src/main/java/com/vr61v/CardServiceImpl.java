package com.vr61v;

import com.vr61v.model.Card;
import com.vr61v.model.CardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private static boolean validateProductUpdate(Card card, UUID userId, String number, CardType type, Float discount) {
        boolean result = true;

        if (userId != null) result = !Objects.equals(card.getUserId(), userId);
        if (number != null) result = !Objects.equals(card.getNumber(), number);
        if (type != null) result = !Objects.equals(card.getType(), type);
        if (discount != null) result = !Objects.equals(card.getDiscount(), discount);

        return result;
    }

    @Override
    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Card getCardById(UUID cardId) {
        return cardRepository.findById(cardId).orElse(null);
    }

    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public Card updateCard(UUID cardId, UUID userId, String number, CardType type, Float discount) {
        Card product = cardRepository
                .findById(cardId)
                .orElseThrow(IllegalArgumentException::new);

        if (!validateProductUpdate(product, userId, number, type, discount)) {
            throw new IllegalArgumentException("the updated fields must be different from the existing ones");
        }

        if (userId != null) product.setUserId(userId);
        if (number != null) product.setNumber(number);
        if (type != null) product.setType(type);
        if (discount != null) product.setDiscount(discount);

        return cardRepository.save(product);
    }

    @Override
    public void deleteCard(UUID cardId) {
        cardRepository.deleteById(cardId);
    }
}
