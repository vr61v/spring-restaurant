package com.vr61v;

import com.vr61v.model.Card;
import com.vr61v.model.CardDto;
import com.vr61v.model.CardMapper;
import com.vr61v.model.request.CreateCardRequest;
import com.vr61v.model.request.UpdateCardRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/cards")
@RequiredArgsConstructor
@Slf4j
public class CardController {

    private final CardService cardService;

    private final CardMapper cardMapper;

    @PostMapping
    public ResponseEntity<CardDto> createCard(@Valid @RequestBody CreateCardRequest createCardRequest) {
        Card card = cardService.createCard(createCardRequest);
        log.info("Created card: {}", card);
        return new ResponseEntity<>(cardMapper.entityToDto(card), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getCard(@PathVariable("id") UUID id) {
        Card card = cardService.getCardById(id);
        log.info("Retrieved card: {}", card);
        if (card == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(cardMapper.entityToDto(card), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CardDto>> getCards() {
        List<Card> cards = cardService.getAllCards();
        log.info("Retrieved cards: {}", cards);
        if (cards.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(
                cards.stream()
                        .map(cardMapper::entityToDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDto> updateCard(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateCardRequest updateCardRequest
    ) {
        Card card;
        try {
            card = cardService.updateCard(id, updateCardRequest);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Updated card: {}", card);
        return new ResponseEntity<>(cardMapper.entityToDto(card), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteCard(@PathVariable("id") UUID id) {
        try {
            cardService.deleteCard(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("Deleted card: {}", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
