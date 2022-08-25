package com.mindhub.homebanking.service.abstraction;

import com.mindhub.homebanking.DTO.ClientDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IClientService {

    ClientDTO getClient(Long id);
    List<ClientDTO> getClients();
    ClientDTO getAuthenticatedUserDetails(Authentication authentication);
    void register(String firstName, String lastName,
                  String email, String password) throws IllegalArgumentException;
    void delete(Long id);
}
