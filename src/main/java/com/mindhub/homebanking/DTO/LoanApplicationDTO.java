package com.mindhub.homebanking.DTO;

public class LoanApplicationDTO {
    private Long id;
    private Double amount;
    private Integer payments;
    private String accountNumber;
    private Double percentage;


    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Double getPercentage() {
        return percentage;
    }
}