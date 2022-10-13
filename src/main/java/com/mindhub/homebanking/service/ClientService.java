package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.common.mail.EmailHelper;
import com.mindhub.homebanking.common.mail.template.RegisterTemplateEmail;
import com.mindhub.homebanking.exceptions.EmailAlreadyExistException;
import com.mindhub.homebanking.exceptions.InvalidCredentialsException;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import com.mindhub.homebanking.exceptions.SendEmailException;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.abstraction.IClientService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailHelper emailHelper;
    Logger logger = LoggerFactory.getLogger(ClientService.class);
    @Override
    public ClientDTO getClient(Long id) {
        return clientRepository.findById(id).map(ClientDTO::new)
                .orElse(null);
    }

    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @Override
    public ClientDTO getAuthenticatedUserDetails(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()).get());
    }

    @Transactional
    @Override
    public void register(String firstName, String lastName, String email, String password)
            throws EmailAlreadyExistException, InvalidCredentialsException, SendEmailException {
        Optional<Client> clientInDDBB = clientRepository.findByEmail(email);
        validate(firstName, lastName, email, password, clientInDDBB);
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);
        newAccount(client);
        //sendEmail(email);
    }

    private void sendEmail(String email) throws SendEmailException {
        try {
            emailHelper.send(new RegisterTemplateEmail(email));
        } catch (SendEmailException e) {
            throw new SendEmailException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) throws InvalidParameterException {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            client.setSoftDelete(true);
            client.getAccounts().stream().forEach(account ->
            {
                account.setSoftDelete(true);
                account.getTransactions().stream().forEach(transaction -> transaction.setSoftDelete(true));
            });
            client.getCards().stream().forEach(card -> card.setSoftDelete(true));
            client.getClientLoans().stream().forEach(clientLoan -> clientLoan.setSoftDelete(true));
        } else {
            throw new InvalidParameterException("The ID doesn't correspond to a real or active client");
        }
    }

    private void newAccount(Client client) {
        Account account = new Account(createNumber(), LocalDate.now(), 0.0, AccountType.CORRIENTE);
        client.addAccount(account);
        accountRepository.save(account);
    }

    private String createNumber() {
        String number;
        do {
            number = "VIN-" + AccountUtils.getAccountNumber();
        } while (!accountRepository.findByNumber(number).isEmpty());
        return number;
    }

    private void validate(String firstName, String lastName, String email, String password,
                          Optional<Client> clientInDDBB)
            throws EmailAlreadyExistException, InvalidCredentialsException {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
                || password.isEmpty() || password.length() < 6) {
            throw new InvalidCredentialsException();
        }
        if (clientInDDBB.isPresent()) {
            logger.error("");
            throw new EmailAlreadyExistException();
        }
    }
}
