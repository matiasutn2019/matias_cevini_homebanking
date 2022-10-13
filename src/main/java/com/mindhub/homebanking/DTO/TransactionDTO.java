package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDate;

public class TransactionDTO {

    private Long id;

    private TransactionType type;

    private LocalDate date;

    private double amount;

    private String description;

    private Double accountBalance;
    private Boolean softDelete;

    public TransactionDTO() {
    }

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.accountBalance = transaction.getAccountBalance();
        this.softDelete = transaction.getSoftDelete();
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public Boolean getSoftDelete() {
        return softDelete;
    }
}
