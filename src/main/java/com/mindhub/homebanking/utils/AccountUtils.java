package com.mindhub.homebanking.utils;

public final class AccountUtils {

    private AccountUtils() {}

    public static String getAccountNumber() {
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int num2 = (int) (Math.random() * 10);
            num.append(num2);
        }
        return num.toString();
    }
}
