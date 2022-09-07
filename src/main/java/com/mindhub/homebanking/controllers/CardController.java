package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.exceptions.CardColorException;
import com.mindhub.homebanking.exceptions.CardTypeException;
import com.mindhub.homebanking.service.abstraction.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api")
public class CardController {

    @Autowired
    private ICardService cardService;

    @PostMapping(value = "/clients/current/cards")
    public ResponseEntity<?> createCard(@RequestParam String cardType, @RequestParam String cardColor,
                                             Authentication authentication) throws CardTypeException, CardColorException {
            cardService.createCard(cardType, cardColor, authentication);
            return new ResponseEntity<>(CREATED);
    }

    @GetMapping(value = "/clients/current/cards")
    public ResponseEntity<List<CardDTO>> cardList(Authentication authentication) {
        return new ResponseEntity<>(cardService.getCards(authentication), OK);
    }
}
