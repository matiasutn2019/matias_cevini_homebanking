package com.mindhub.homebanking.models;

import java.util.stream.Stream;

public enum AccountType {

    CORRIENTE("CORRIENTE"),
    AHORRO("AHORRO");

    private String accountType;

    AccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }

    public static Stream<AccountType> stream() {
        return Stream.of(AccountType.values());
    }
}
