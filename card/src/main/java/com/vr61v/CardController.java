package com.vr61v;

import com.vr61v.model.CardDto;
import com.vr61v.model.Card;
import com.vr61v.model.CardMapper;
import com.vr61v.model.CardType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    private final CardMapper cardMapper;

    @PostMapping
    public ResponseEntity<CardDto> createCard(@RequestBody CardDto cardDto) {
        Card card = cardService.createCard(cardMapper.dtoToEntity(cardDto));
        return new ResponseEntity<>(cardMapper.entityToDto(card), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getCard(@PathVariable("id") UUID id) {
        Card card = cardService.getCardById(id);
        return new ResponseEntity<>(cardMapper.entityToDto(card), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CardDto>> getCards() {
        List<Card> cards = cardService.getAllCards();
        return new ResponseEntity<>(
                cards.stream()
                        .map(cardMapper::entityToDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDto> updateCard(
            @PathVariable("id") UUID id,
            @RequestParam(required = false, value = "userId") UUID userId,
            @RequestParam(required = false, value = "number") String number,
            @RequestParam(required = false, value = "type") CardType type,
            @RequestParam(required = false, value = "discount") Float discount
    ) {
        Card card = cardService.updateCard(id, userId, number, type, discount);
        return new ResponseEntity<>(cardMapper.entityToDto(card), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteCard(@PathVariable("id") UUID id) {
        cardService.deleteCard(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
