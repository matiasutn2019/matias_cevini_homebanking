package com.mindhub.homebanking.service.abstraction;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.exceptions.CardColorException;
import com.mindhub.homebanking.exceptions.CardTypeException;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICardService {

    void createCard(String cardType, String cardColor, Authentication authentication) throws CardTypeException, CardColorException;

    List<CardDTO> getCards(Authentication authentication);
    void deleteCard(String number, Authentication authentication) throws InvalidParameterException;
}
