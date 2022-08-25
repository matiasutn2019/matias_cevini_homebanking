package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.LoanType;

public class ClientLoanDTO {

    private Long idClientLoan;
    private Long idLoan;
    private LoanType name;
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

    public LoanType getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayment() {
        return payment;
    }
}