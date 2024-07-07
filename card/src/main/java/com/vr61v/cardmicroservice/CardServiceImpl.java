package com.vr61v.cardmicroservice;

import com.vr61v.cardmicroservice.model.Card;
import com.vr61v.cardmicroservice.model.request.CreateCardRequest;
import com.vr61v.cardmicroservice.model.request.UpdateCardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    @PreAuthorize("hasRole(\"ADMIN\")")
    public Card createCard(CreateCardRequest createCardRequest) {
        Card card = Card.builder()
                .id(UUID.randomUUID())
                .userId(createCardRequest.userId())
                .number(createCardRequest.number())
                .type(createCardRequest.type())
                .discount(createCardRequest.discount())
                .build();
        return cardRepository.save(card);
    }

    @Override
    @PostAuthorize("returnObject == null || " +
            "hasRole(\"CUSTOMER\") && returnObject.userId.toString() == authentication.principal.getClaims().get(\"sub\") || " +
            "hasRole(\"ADMIN\")")
    public Card getCardById(UUID cardId) {
        return cardRepository.findById(cardId).orElse(null);
    }

    @Override
    @PostAuthorize("returnObject == null || " +
            "hasRole(\"CUSTOMER\") && returnObject.userId.toString() == authentication.principal.getClaims().get(\"sub\") || " +
            "hasRole(\"ADMIN\")")
    public Card getCardByNumber(String number) {
        return cardRepository.findByNumber(number).orElse(null);
    }

    @Override
    @PostFilter("hasRole(\"CUSTOMER\") && filterObject.userId.toString() == authentication.principal.getClaims().get(\"sub\") || " +
            "hasRole(\"ADMIN\")")
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole(\"ADMIN\")")
    public Card updateCard(UUID cardId, UpdateCardRequest updateCardRequest) {
        Card product = cardRepository
                .findById(cardId)
                .orElseThrow(IllegalArgumentException::new);

        if (updateCardRequest.userId() != null) product.setUserId(updateCardRequest.userId());
        if (updateCardRequest.number() != null) product.setNumber(updateCardRequest.number());
        if (updateCardRequest.type() != null) product.setType(updateCardRequest.type());
        if (updateCardRequest.discount() != null) product.setDiscount(updateCardRequest.discount());

        return cardRepository.save(product);
    }

    @Override
    @PreAuthorize("hasAnyRole(\"CUSTOMER\", \"ADMIN\")")
    public void deleteCardById(UUID cardId) {
        cardRepository.deleteById(cardId);
    }

    @Override
    @PreAuthorize("hasAnyRole(\"CUSTOMER\", \"ADMIN\")")
    public void deleteCardByNumber(String number) {
        cardRepository.deleteByNumber(number);
    }
}
