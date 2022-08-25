package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.abstraction.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public void createCard(String cardType, String cardColor, Authentication authentication) throws Exception {
        Client client = clientRepository.findByEmail(authentication.getName()).get();
        validateCards(client.getCards(), cardType);
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
        return new Card(CardType.valueOf(cardType), createNumber(),
                getRandom(3), LocalDate.now(), LocalDate.now().plusYears(5),
                client.getFirstName() + client.getLastName(), CardColor.valueOf(cardColor));
    }

    private String getRandom(int size) {
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int num2 = (int) (Math.random() * 10);
            num.append(num2);
        }
        return num.toString();
    }

    private String createNumber() {
        String number;
        do {
            number = getRandom(4) + " " + getRandom(4) +
                    " " + getRandom(4) + " " + getRandom(4);
        } while (!cardRepository.findByNumber(number).isEmpty());
        return number;
    }

    private void validateCards(Set<Card> cards, String type) throws Exception {
        List<Card> cardsList = cards.stream().filter(x -> x.getType().toString().equals(type)).collect(toList());
        if (cardsList.size() >= 3) {
            throw new Exception("You have reached the card limit of type " + type);
        }
    }
}
