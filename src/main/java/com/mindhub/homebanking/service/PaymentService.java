package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.PaymentDTO;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.abstraction.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @Override
    public void handlePayment(PaymentDTO payment) throws InvalidParameterException {
        validateRequest(payment);
        Optional<Card> card = cardRepository.findByNumber(payment.getCardNumber());
        validateCard(card, payment);
        Optional<Account> accountDestination = accountRepository.findByNumber(payment.getAccountDestination());
        validateDestinationAccount(accountDestination);
        Account accountOrigin = findAccountOrigin(card.get(), payment.getAmountPayment());
        validateAccountOrigin(accountOrigin);

        makeTransaction((0 - payment.getAmountPayment()), payment.getDescription(),
                accountOrigin, DEBIT, accountDestination.get().getNumber());//cuenta origen
        makeTransaction(payment.getAmountPayment(), payment.getDescription(),
                accountDestination.get(), CREDIT, accountOrigin.getNumber());//cuenta destino
    }

    private void makeTransaction(Double amount, String description, Account account,
                                 TransactionType type, String numberOtherAccount) {
        Transaction transaction =
                new Transaction(type, LocalDate.now(), amount,
                        description + " " + numberOtherAccount, getAccountBalance(account, amount));
        account.setBalance(account.getBalance() + amount);
        account.addTransaction(transaction);
        transactionRepository.save(transaction);
    }

    private double getAccountBalance(Account account, Double amountTransaction) {
        return account.getBalance() + amountTransaction;
    }

    private void validateAccountOrigin(Account accountOrigin) throws InvalidParameterException {
        if (accountOrigin == null) {
            throw new InvalidParameterException("There is no account with sufficient balance");
        }
    }

    private Account findAccountOrigin(Card card, Double payment) {
        return card.getClient().getAccounts().stream()
                .filter(account ->
                        account.getBalance() >= payment)
                .findFirst().orElse(null);
    }

    private void validateDestinationAccount(Optional<Account> account) throws InvalidParameterException {
        if (account.isEmpty()) {
            throw new InvalidParameterException("The destination account doesn't exist");
        }
    }

    private boolean validateAmount(Account accountOrigin, Double payment) throws InvalidParameterException {
            //throw new InvalidParameterException("Insufficient account balance");
        return accountOrigin.getBalance() < payment;
    }

    private void validateCard(Optional<Card> card, PaymentDTO payment) throws InvalidParameterException {
        if (card.isEmpty()) {
            throw new InvalidParameterException("The number doesn't correspond to the Card");
        }
        if (!card.get().getCardHolder().equals(payment.getCardHolder())) {
            throw new InvalidParameterException("The holder doesn't correspond to the Card");
        }
        if (!card.get().getCvv().equals(payment.getCardCvv())) {
            throw new InvalidParameterException("Invalid CVV number");
        }
        if (!card.get().getType().toString().equals("DEBIT")) {
            throw new InvalidParameterException("The card isn't a debit card");
        }
    }

    private void validateRequest(PaymentDTO payment) throws InvalidParameterException {
        if (payment.getCardNumber().isEmpty()) {
            throw new InvalidParameterException("Card number is empty");
        }
        if (payment.getCardHolder().isEmpty()) {
            throw new InvalidParameterException("Card holder is empty");
        }
        if (payment.getCardCvv().isEmpty()) {
            throw new InvalidParameterException("CVV number is empty");
        }
        if (payment.getAmountPayment() <= 0) {
            throw new InvalidParameterException("Amount is <= zero");
        }
        if (payment.getAccountDestination().isEmpty()) {
            throw new InvalidParameterException("Destination account is empty");
        }
        if (payment.getDescription().isEmpty()) {
            throw new InvalidParameterException("Description is empty");
        }
    }
}
