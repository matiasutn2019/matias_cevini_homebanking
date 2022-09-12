package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDate;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AccountDTO {

    private Long id;
    private String number;
    private LocalDate creationTime;
    private Double balance;
    private AccountType accountType;
    private Set<TransactionDTO> transactionsDTO;

    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationTime = account.getCreationTime();
        this.balance = account.getBalance();
        this.accountType = account.getAccountType();
        this.transactionsDTO = account.getTransactions().stream().map(TransactionDTO::new).collect(toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public Double getBalance() {
        return balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Set<TransactionDTO> getTransactionsDTO() {
        return transactionsDTO;
    }
}
