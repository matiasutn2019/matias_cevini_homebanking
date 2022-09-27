package com.mindhub.homebanking.DTO;

public class PaymentDTO {

    private String cardNumber;
    private String cardHolder;
    private String cardCvv;
    private Double amountPayment;
    private String accountDestination;
    private String description;

    public PaymentDTO() {
    }

    public PaymentDTO(String cardNumber, String cardHolder,
                      String cardCvv, Double amountPayment,
                      String accountDestination, String description) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.cardCvv = cardCvv;
        this.amountPayment = amountPayment;
        this.accountDestination = accountDestination;
        this.description = description;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public Double getAmountPayment() {
        return amountPayment;
    }

    public String getAccountDestination() {
        return accountDestination;
    }

    public String getDescription() {
        return description;
    }
}
