package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Client;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ClientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean softDelete;
    private Set<AccountDTO> accountsDTO;
    private Set<ClientLoanDTO> loans;
    private Set<CardDTO> cards;

    public ClientDTO() {}

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.softDelete = client.getSoftDelete();
        this.accountsDTO = client.getAccounts().stream().map(AccountDTO::new).collect(toSet());
        this.loans = client.getClientLoans().stream().map(ClientLoanDTO::new).collect(toSet());
        this.cards = client.getCards().stream().map(CardDTO::new).collect(toSet());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccountsDTO() {
        return accountsDTO;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }

    public Boolean getSoftDelete() {
        return softDelete;
    }
}
