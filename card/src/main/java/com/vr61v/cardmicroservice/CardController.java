package com.vr61v.cardmicroservice;

import com.vr61v.cardmicroservice.model.Card;
import com.vr61v.cardmicroservice.model.CardDto;
import com.vr61v.cardmicroservice.model.CardMapper;
import com.vr61v.cardmicroservice.model.request.CreateCardRequest;
import com.vr61v.cardmicroservice.model.request.UpdateCardRequest;
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
    public ResponseEntity<?> createCard(@Valid @RequestBody CreateCardRequest createCardRequest) {
        Card card;
        try {
            card = cardService.createCard(createCardRequest);
        } catch (IllegalArgumentException | NullPointerException e) {
            log.error("Error create card: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Created card: {}", card);
        return new ResponseEntity<>(cardMapper.entityToDto(card), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<?> getCard(@RequestParam(required = false) UUID id, @RequestParam(required = false) String number) {
        Card card = null;
        if (id != null) card = cardService.getCardById(id);
        else if (number != null) card = cardService.getCardByNumber(number);
        log.info("Retrieved card: {}", card);
        if (card == null) {
            StringBuilder message = new StringBuilder();
            if (id != null) message.append("The card with id:").append(id).append(" was not found");
            else if (number != null) message.append("The card with number:").append(number).append(" was not found");
            else message.append("Id or number must not be null");
            return new ResponseEntity<>(message.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cardMapper.entityToDto(card), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CardDto>> getCards() {
        List<Card> cards = cardService.getAllCards();
        log.info("Retrieved cards: {}", cards);
        return new ResponseEntity<>(
                cards.stream()
                        .map(cardMapper::entityToDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCard(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateCardRequest updateCardRequest
    ) {
        Card card;
        try {
            card = cardService.updateCard(id, updateCardRequest);
        } catch (IllegalArgumentException | NullPointerException e) {
            log.error("Error update card: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Updated card: {}", card);
        return new ResponseEntity<>(cardMapper.entityToDto(card), HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteCard(@RequestParam(required = false) UUID id, @RequestParam(required = false) String number) {
        try {
            if (id != null) cardService.deleteCardById(id);
            else if (number != null) cardService.deleteCardByNumber(number);
            else throw new IllegalArgumentException("Id or number must not be null");
        } catch (IllegalArgumentException | NullPointerException e) {
            log.error("Error delete card: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.info("Deleted card: {}", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
