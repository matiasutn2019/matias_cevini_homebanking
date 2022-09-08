package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.exceptions.CardColorException;
import com.mindhub.homebanking.exceptions.CardTypeException;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.abstraction.ICardService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class CardService implements ICardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void createCard(String cardType, String cardColor, Authentication authentication) throws CardTypeException, CardColorException {
        Client client = clientRepository.findByEmail(authentication.getName()).get();
        validateTypeAndColor(cardType, cardColor);
        validateCardLimit(client.getCards(), cardType, cardColor);
        Card card = newCard(cardType, cardColor, client);
        client.addCard(card);
        cardRepository.save(card);
    }

    @Override
    public List<CardDTO> getCards(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName()).get();
        return client.getCards().stream().map(CardDTO::new).collect(toList());
    }

    private Card newCard(String cardType, String cardColor, Client client) {
        return new Card(CardType.valueOf(cardType), validateCardNumber(),
                CardUtils.getCVV(), LocalDate.now(), LocalDate.now().plusYears(5),
                client.getFirstName() + client.getLastName(), CardColor.valueOf(cardColor));
    }

    private void validateTypeAndColor(String cardType, String cardColor) throws CardTypeException, CardColorException {
        if (Arrays.stream(CardType.values()).noneMatch(c -> c.toString().equals(cardType))) {
            throw new CardTypeException("Invalid Card type.");
        }
        if (Arrays.stream(CardColor.values()).noneMatch(c -> c.toString().equals(cardColor))) {
            throw new CardColorException("Invalid Card color.");
        }
    }

    private void validateCardLimit(Set<Card> cards, String type, String color) throws CardTypeException, CardColorException {
        List<Card> filteredCards = cards.stream().filter(x -> x.getType().toString().equals(type)).collect(toList());
        if (filteredCards.size() >= 3) {
            throw new CardTypeException("You have reached the card limit of this type.");
        } else if (filteredCards.stream().anyMatch(x -> x.getColor().toString().equals(color))) {
            throw new CardColorException("You already have a card of this color.");
        }
    }

    private String validateCardNumber() {
        String cardNumber = "";
        do {
            cardNumber = CardUtils.getCardNumber();
        } while (cardRepository.findByNumber(cardNumber).isPresent());
        return cardNumber;
    }
}
