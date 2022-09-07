package com.mindhub.homebanking.exceptions;

public class CardColorException extends Exception {

    private final static String CARD_COLOR_MESSAGE = "You already have a card of this color.";

    private static final long serialVersionUID = 1L;

    public CardColorException() {
        super(CARD_COLOR_MESSAGE);
    }
}
