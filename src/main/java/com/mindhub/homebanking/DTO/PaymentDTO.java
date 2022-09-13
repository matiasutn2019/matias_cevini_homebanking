package com.mindhub.homebanking.DTO;

public class PaymentDTO {

    private String number;
    private String cvv;
    private Double amount;
    private String description;

    public PaymentDTO() {
    }

    public PaymentDTO(String number, String cvv, Double amount, String description) {
        this.number = number;
        this.cvv = cvv;
        this.amount = amount;
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public String getCvv() {
        return cvv;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
