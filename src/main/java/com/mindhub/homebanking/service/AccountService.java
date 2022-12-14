package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.exceptions.AccountLimitException;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.abstraction.IAccountService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<AccountDTO> accountList() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public AccountDTO getById(Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @Override
    public void createAccount(Authentication authentication, AccountType accountType) throws AccountLimitException {
        Client client = clientRepository.findByEmail(authentication.getName()).get();
        validateAccountLimit(client);
        Account account = new Account(createNumber(), LocalDate.now(), 0.0, accountType);
        client.addAccount(account);
        accountRepository.save(account);
    }

    @Override
    public List<AccountDTO> getAccounts(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName()).get();
        return client.getAccounts().stream()
                .filter(account -> account.getSoftDelete().equals(false))
                .map(AccountDTO::new).collect(toList());
    }

    @Override
    public void deleteAccount(String number, Authentication authentication) throws InvalidParameterException {
        Client client = clientRepository.findByEmail(authentication.getName()).get();
        validateAccountNumber(number);
        Optional<Account> account = client.getAccounts().stream().filter(a -> a.getNumber().equals(number)).findFirst();
        validateAccountExist(account);
        account.get().setSoftDelete(true);
        deleteTransactions(account.get());
        accountRepository.save(account.get());
    }

    @Override
    public List<String> getTypes() {
        return AccountType.stream().map(type ->type.getAccountType()).collect(toList());
    }

    private String createNumber() {
        String number;
        do {
            number = "VIN-" + AccountUtils.getAccountNumber();
        } while (!accountRepository.findByNumber(number).isEmpty());
        return number;
    }

    private void validateAccountNumber(String number) throws InvalidParameterException {
        if (number.isEmpty()) {
            throw new InvalidParameterException("Account number is empty");
        }
    }

    private void validateAccountExist(Optional account) throws InvalidParameterException {
        if (account.isEmpty()) {
            throw new InvalidParameterException("There's no Account with that number.");
        }
    }

    private void validateAccountLimit(Client client) throws AccountLimitException {
        if (client.getAccounts().stream().filter(account -> account.getSoftDelete().equals(false)).count() >= 3) {
            throw new AccountLimitException();
        }
    }

    private void deleteTransactions(Account account) {
        Set<Transaction> transactions = account.getTransactions();
        transactions.forEach(transaction -> transaction.setSoftDelete(true));
    }
}

