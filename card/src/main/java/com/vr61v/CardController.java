package com.vr61v;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        Card created = cardService.createCard(card);
        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCard(@PathVariable("id") UUID id) {
        Card card = cardService.getCardById(id);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Card>> getCards() {
        List<Card> cards = cardService.getAllCards();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Card> updateCard(
            @PathVariable("id") UUID id,
            @RequestParam(required = false, value = "userId") UUID userId,
            @RequestParam(required = false, value = "number") String number,
            @RequestParam(required = false, value = "type") CardType type,
            @RequestParam(required = false, value = "discount") Float discount
    ) {
        Card update = cardService.updateCard(id, userId, number, type, discount);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteCard(@PathVariable("id") UUID id) {
        cardService.deleteCard(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
