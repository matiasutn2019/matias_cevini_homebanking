package com.mindhub.homebanking.service.abstraction;

import com.mindhub.homebanking.DTO.CardDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICardService {

    void createCard(String cardType, String cardColor, Authentication authentication) throws Exception;

    List<CardDTO> getCards(Authentication authentication);
}
