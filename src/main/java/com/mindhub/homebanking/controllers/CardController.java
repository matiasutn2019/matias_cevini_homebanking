package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.common.DocumentationMessages;
import com.mindhub.homebanking.exceptions.CardColorException;
import com.mindhub.homebanking.exceptions.CardTypeException;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import com.mindhub.homebanking.service.abstraction.ICardService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(
            value = DocumentationMessages.CARD_CONTROLLER_CREATE,
            notes = DocumentationMessages.CARD_CONTROLLER_CREATE_DESCRIPTION,
            response = ResponseEntity.class
    )
    @PostMapping(value = "/clients/current/cards")
    public ResponseEntity<?> createCard(@RequestParam String cardType, @RequestParam String cardColor,
                                             Authentication authentication)
            throws CardTypeException, CardColorException {
            cardService.createCard(cardType, cardColor, authentication);
            return new ResponseEntity<>(CREATED);
    }

    @ApiOperation(
            value = DocumentationMessages.CARD_CONTROLLER_GET,
            notes = DocumentationMessages.CARD_CONTROLLER_GET_DESCRIPTION,
            response = ResponseEntity.class
    )
    @GetMapping(value = "/clients/current/cards")
    public ResponseEntity<List<CardDTO>> cardList(Authentication authentication) {
        return new ResponseEntity<>(cardService.getCards(authentication), OK);
    }

    @ApiOperation(
            value = DocumentationMessages.CARD_CONTROLLER_DELETE,
            notes = DocumentationMessages.CARD_CONTROLLER_DELETE_DESCRIPTION,
            response = ResponseEntity.class
    )
    @PatchMapping(value = "/clients/current/cards")
    public ResponseEntity<?> deleteCard(@RequestParam String number, Authentication authentication)
            throws InvalidParameterException {
        cardService.deleteCard(number, authentication);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
