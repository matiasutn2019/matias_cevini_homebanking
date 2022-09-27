package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.exceptions.EmailAlreadyExistException;
import com.mindhub.homebanking.exceptions.InvalidCredentialsException;
import com.mindhub.homebanking.service.abstraction.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping(value = "/admin/clients")
    public ResponseEntity<List<ClientDTO>> getClients() {
        return new ResponseEntity<>(clientService.getClients(), OK);
    }

    @GetMapping(value = "/admin/clients/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(clientService.getClient(id), OK);
    }

    @GetMapping(value = "/clients/current")
    public ResponseEntity<ClientDTO> getAuthenticatedUserDetails(Authentication authentication) {
        return new ResponseEntity<>(clientService.getAuthenticatedUserDetails(authentication), OK);
    }

    @PostMapping(path = "/clients")
    public ResponseEntity<?> register(@RequestParam String firstName, @RequestParam String lastName,
                                  @RequestParam String email, @RequestParam String password)
            throws EmailAlreadyExistException, InvalidCredentialsException {
            clientService.register(firstName, lastName, email, password);
            return new ResponseEntity<>(CREATED);
    }

    @DeleteMapping(value = "/admin/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable(name = "id") Long id) {
        clientService.delete(id);
        return new ResponseEntity<>(id, NO_CONTENT);
    }
}
