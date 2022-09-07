package com.mindhub.homebanking.exceptions;

public class InvalidCredentialsException extends Exception {

    private final static String INVALID_PARAMETERS_MESSAGE = "Invalid parameters.";

    private static final long serialVersionUID = 1L;

    public InvalidCredentialsException() {
        super(INVALID_PARAMETERS_MESSAGE);
    }
}
