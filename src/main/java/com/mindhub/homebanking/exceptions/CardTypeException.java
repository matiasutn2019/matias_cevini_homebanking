package com.mindhub.homebanking.exceptions;

public class CardTypeException extends Exception {

    private final static String CARD_TYPE_MESSAGE = "You have reached the card limit of this type.";

    private static final long serialVersionUID = 1L;

    public CardTypeException() {
        super(CARD_TYPE_MESSAGE);
    }
}
