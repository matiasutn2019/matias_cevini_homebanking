package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private Long idClientLoan;
    private Long idLoan;
    private String name;
    private Double amount;
    private Integer payment;

    public ClientLoanDTO() {}

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.idClientLoan = clientLoan.getId();
        this.idLoan = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payment = clientLoan.getPayments();
    }

    public Long getIdClientLoan() {
        return idClientLoan;
    }

    public Long getIdLoan() {
        return idLoan;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayment() {
        return payment;
    }
}