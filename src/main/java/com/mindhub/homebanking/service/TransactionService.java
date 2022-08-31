package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.abstraction.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @Override
    public void transaction(Double amount, String description, String accountOriginNumber,
                            String accountDestinationNumber, Authentication authentication) throws IllegalArgumentException {
        Client client = clientRepository.findByEmail(authentication.getName()).get();
        Account accountOrigin = accountRepository.findByNumber(accountOriginNumber).orElse(null);
        Account accountDestination = accountRepository.findByNumber(accountDestinationNumber).orElse(null);
        validation(client, accountOrigin, accountDestination, amount, description, accountOriginNumber, accountDestinationNumber);
        makeTransaction((0 - amount), description, accountOrigin, DEBIT, accountDestination.getNumber());//cuenta origen
        makeTransaction(amount, description, accountDestination, CREDIT, accountOrigin.getNumber());//cuenta destino
    }

    private void validation(Client client, Account accountOrigin, Account accountDestination, Double amount,
                              String description, String accountOriginNumber, String accountDestinationNumber) throws IllegalArgumentException {
        if (amount.toString().isEmpty() || description.isEmpty() || accountOriginNumber.isEmpty() || accountDestinationNumber.isEmpty()) {
            throw new IllegalArgumentException("One or more of the parameters is empty");
            //Verificar que los parámetros no estén vacíos
        }
        if (amount.isNaN() || amount <= 0) {
            throw new IllegalArgumentException("The parameter amount insn't a number or is <= 0");
        }
        if (accountOriginNumber.equals(accountDestinationNumber)) {
            throw new IllegalArgumentException("Equal card numbers");
            //Verificar que los números de cuenta no sean iguales
        }
        if (accountOrigin == null) {
            throw new IllegalArgumentException("Origin account doesn't exist");
            //Verificar que exista la cuenta de origen
        }
        if (!client.getAccounts().contains(accountOrigin)) {
            throw new IllegalArgumentException("Origin account doesn't belong to authenticated client");
            //Verificar que la cuenta de origen pertenezca al cliente autenticado
        }
        if (accountDestination == null) {
            throw new IllegalArgumentException("Destination account doesn't exist");
            //Verificar que exista la cuenta de destino
        }
        if (accountOrigin.getBalance() < amount) {
            throw new IllegalArgumentException("Origin account's balance not enough");
            //Verificar que la cuenta de origen tenga el monto disponible
        }
    }

    private void makeTransaction(Double amount, String description, Account account, TransactionType type, String numberOtherAccount) {
        Transaction transaction = new Transaction(type, LocalDate.now(), amount, description + " " + numberOtherAccount);
        account.setBalance(account.getBalance() + amount);
        account.addTransaction(transaction);
        transactionRepository.save(transaction);
    }
}