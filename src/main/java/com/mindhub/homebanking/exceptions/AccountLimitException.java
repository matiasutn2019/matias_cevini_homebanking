package com.mindhub.homebanking.exceptions;

public class AccountLimitException extends Exception {
    private final static String ACCOUNT_LIMIT_MESSAGE = "You have reached the account limit.";

    private static final long serialVersionUID = 1L;

    public AccountLimitException() {
        super(ACCOUNT_LIMIT_MESSAGE);
    }

}
