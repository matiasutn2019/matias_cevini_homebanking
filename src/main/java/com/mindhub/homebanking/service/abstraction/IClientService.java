package com.mindhub.homebanking.service.abstraction;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.exceptions.EmailAlreadyExistException;
import com.mindhub.homebanking.exceptions.InvalidCredentialsException;
import com.mindhub.homebanking.exceptions.SendEmailException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IClientService {

    ClientDTO getClient(Long id);
    List<ClientDTO> getClients();
    ClientDTO getAuthenticatedUserDetails(Authentication authentication);
    void register(String firstName, String lastName,
                  String email, String password) throws EmailAlreadyExistException, InvalidCredentialsException, SendEmailException;
    void delete(Long id);
}
