package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

//@DataJpaTest // sólo si se usa postgresql
@AutoConfigureTestDatabase(replace = NONE) // con h2 sí, postgresql no se
// las anot. de arriba indican a Spring que debe escanear en busca de clases @Entity y configurar los repositorios JPA
@SpringBootTest // sólo si se usa h2
public class RepositoriesTest {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void existLoans() {
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }

    @Test
    public void existPersonalLoan() {
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("PERSONAL"))));
    }

    @Test
    public void existAccounts() {
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }

    @Test
    public void existSingleAccount() {
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, hasItem(hasProperty("number", is("VIN-001"))));
    }

    @Test
    public void existCards() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }

    @Test
    public void existSingleCard() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("number", is("0000 1111 2222 3333"))));
    }

    @Test
    public void existClients() {
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }

    @Test
    public void existSingleClient() {
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("email", is("melba@gmail.com"))));
    }

    @Test
    public void existsTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));
    }

    @Test
    public void existSingleTransaction() {
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, hasItem(hasProperty("description", is("crédito de $ 1000 a la cuenta 1"))));
    }
}