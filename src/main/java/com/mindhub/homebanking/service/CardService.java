package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.exceptions.CardColorException;
import com.mindhub.homebanking.exceptions.CardTypeException;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
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

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        return client.getCards().stream()
                .filter(card -> card.getSoftDelete().equals(false))
                .map(CardDTO::new).collect(toList());
    }

    @Override
    public void deleteCard(String number, Authentication authentication)
            throws InvalidParameterException {
        Client client = clientRepository.findByEmail(authentication.getName()).get();
        validateCardNumber(number);
        Optional<Card> card = client.getCards().stream().filter(c -> c.getNumber().equals(number)).findFirst();
        validateCardExist(card);
        card.get().setSoftDelete(true);
        cardRepository.save(card.get());
    }

    private void validateCardExist(Optional card) throws InvalidParameterException {
        if (card.isEmpty()) {
            throw new InvalidParameterException("There's no Card with that number.");
        }
    }

    private void validateCardNumber(String number) throws InvalidParameterException {
        if (number.isEmpty()) {
            throw new InvalidParameterException("Card number is empty");
        }
        if (number.length() <= 18 || number.length() >= 20 ) {
            throw new InvalidParameterException("Invalid Card number length");
        }
    }

    private Card newCard(String cardType, String cardColor, Client client) {
        return new Card(CardType.valueOf(cardType), createCardNumber(),
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
        List<Card> filteredCards = cards.stream()
                .filter(card -> card.getSoftDelete().equals(false))
                .filter(card -> card.getType().toString().equals(type))
                .collect(toList());
        if (filteredCards.size() >= 3) {
            throw new CardTypeException("You have reached the card limit of this type.");
        } else if (filteredCards.stream().anyMatch(x -> x.getColor().toString().equals(color))) {
            throw new CardColorException("You already have a card of this color.");
        }
    }

    private String createCardNumber() {
        String cardNumber = "";
        do {
            cardNumber = CardUtils.getCardNumber();
        } while (cardRepository.findByNumber(cardNumber).isPresent());
        return cardNumber;
    }
}
