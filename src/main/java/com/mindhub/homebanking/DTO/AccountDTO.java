package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;
import java.time.LocalDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AccountDTO {

    private Long id;
    private String number;
    private LocalDateTime creationTime;
    private Double balance;
    private Set<TransactionDTO> transactionsDTO;

    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationTime = account.getCreationTime();
        this.balance = account.getBalance();
        this.transactionsDTO = account.getTransactions().stream().map(TransactionDTO::new).collect(toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public Double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransactionsDTO() {
        return transactionsDTO;
    }
}
