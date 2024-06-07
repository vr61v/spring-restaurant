package com.vr61v;

import com.vr61v.model.Card;
import com.vr61v.model.request.CreateCardRequest;
import com.vr61v.model.request.UpdateCardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private static boolean validateProductUpdate(Card card, UpdateCardRequest updateCardRequest) {
        boolean result = true;

        if (updateCardRequest.userId() != null) result &= !Objects.equals(card.getUserId(), updateCardRequest.userId());
        if (updateCardRequest.number() != null) result &= !Objects.equals(card.getNumber(), updateCardRequest.number());
        if (updateCardRequest.type() != null) result &= !Objects.equals(card.getType(), updateCardRequest.type());
        if (updateCardRequest.discount() != null) result &= !Objects.equals(card.getDiscount(), updateCardRequest.discount());

        return result;
    }

    @Override
    public Card createCard(CreateCardRequest createCardRequest) {
        Card card = Card.builder()
                .userId(createCardRequest.userId())
                .number(createCardRequest.number())
                .type(createCardRequest.type())
                .discount(createCardRequest.discount())
                .build();
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
    public Card updateCard(UUID cardId, UpdateCardRequest updateCardRequest) {
        Card product = cardRepository
                .findById(cardId)
                .orElseThrow(IllegalArgumentException::new);

        if (!validateProductUpdate(product, updateCardRequest)) {
            throw new IllegalArgumentException("the updated fields must be different from the existing ones");
        }

        if (updateCardRequest.userId() != null) product.setUserId(updateCardRequest.userId());
        if (updateCardRequest.number() != null) product.setNumber(updateCardRequest.number());
        if (updateCardRequest.type() != null) product.setType(updateCardRequest.type());
        if (updateCardRequest.discount() != null) product.setDiscount(updateCardRequest.discount());

        return cardRepository.save(product);
    }

    @Override
    public void deleteCard(UUID cardId) {
        cardRepository.deleteById(cardId);
    }
}
