package com.mindhub.homebanking.utils;

public final class CardUtils {

    public static String getCVV() {
        return (int) ((Math.random() * (999-100)) + 100) + "";
    }

    public static String getCardNumber() {
        return
                (int) ((Math.random() * (9999-1000)) + 1000) + " " +
                (int) ((Math.random() * (9999-1000)) + 1000) + " " +
                (int) ((Math.random() * (9999-1000)) + 1000) + " " +
                (int) ((Math.random() * (9999-1000)) + 1000);
    }
}
