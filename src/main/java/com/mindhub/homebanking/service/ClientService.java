package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.exceptions.EmailAlreadyExistException;
import com.mindhub.homebanking.exceptions.InvalidCredentialsException;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.abstraction.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Override
    public ClientDTO getClient(Long id) {
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @Override
    public ClientDTO getAuthenticatedUserDetails(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()).get());
    }

    @Override
    public void register(String firstName, String lastName, String email, String password) throws EmailAlreadyExistException, InvalidCredentialsException {
        Optional<Client> clientInDDBB = clientRepository.findByEmail(email);
        validate(firstName, lastName, email, password, clientInDDBB);
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);
        newAccount(client);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    private void newAccount(Client client) {
        Account account = new Account(createNumber(), LocalDate.now(), 0.0);
        client.addAccount(account);
        accountRepository.save(account);
    }

    private String createNumber() {
        String number;
        do {
            number = "VIN-" + getRandom(8);
        } while (!accountRepository.findByNumber(number).isEmpty());
        return number;
    }

    private String getRandom(int size) {
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int num2 = (int) (Math.random() * 10);
            num.append(num2);
        }
        return num.toString();
    }

    private void validate(String firstName, String lastName, String email, String password,
                          Optional<Client> clientInDDBB) throws EmailAlreadyExistException, InvalidCredentialsException {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw new InvalidCredentialsException();
        }
        if (clientInDDBB.isPresent()) {
            throw new EmailAlreadyExistException();
        }
    }
}
