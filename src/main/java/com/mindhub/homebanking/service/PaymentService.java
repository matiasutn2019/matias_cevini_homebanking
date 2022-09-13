package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.PaymentDTO;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.abstraction.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @Override
    public void handlePayment(Authentication authentication, PaymentDTO payment) throws InvalidParameterException {
        validateRequest(payment);
        Client client = clientRepository.findByEmail(authentication.getName()).get();
        Account account = client.getAccounts().stream().filter(a -> a.getAccountType().toString().equals("CORRIENTE")).findFirst().get();
        validateCard(client, payment);
        validateAmount(account, payment);
        makePayment(account, payment);
    }

    private void makePayment(Account account, PaymentDTO payment) {
        Double newAccountBalance = account.getBalance() - payment.getAmount();
        Transaction transaction =
                new Transaction(TransactionType.DEBIT, LocalDate.now(),
                        0 - payment.getAmount(), payment.getDescription(),
                        newAccountBalance);
        account.addTransaction(transaction);
        transactionRepository.save(transaction);
        account.setBalance(newAccountBalance);
    }

    private void validateAmount(Account account, PaymentDTO payment) throws InvalidParameterException {
        if (account.getBalance() < payment.getAmount()) {
            throw new InvalidParameterException("Insufficient account balance");
        }
    }

    private void validateCard(Client client, PaymentDTO payment) throws InvalidParameterException {
        Optional<Card> card = client.getCards().stream().filter(c -> c.getNumber().equals(payment.getNumber())).findFirst();
        if (card.isEmpty()) {
            throw new InvalidParameterException("The number doesn't correspond to a Card");
        }
        if (!card.get().getCvv().equals(payment.getCvv())) {
            throw new InvalidParameterException("Invalid CVV number");
        }
        if (!card.get().getType().toString().equals("DEBIT")) {
            throw new InvalidParameterException("The card isn't a debit card");
        }
    }

    private void validateRequest(PaymentDTO payment) throws InvalidParameterException {
        if (payment.getNumber().isEmpty()) {
            throw new InvalidParameterException("Card number is empty");
        }
        if (payment.getCvv().isEmpty()) {
            throw new InvalidParameterException("CVV number is empty");
        }
        if (payment.getAmount() == 0) {
            throw new InvalidParameterException("Amount is zero");
        }
        if (payment.getDescription().isEmpty()) {
            throw new InvalidParameterException("Description is empty");
        }
    }
}
