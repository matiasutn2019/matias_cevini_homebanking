package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.service.abstraction.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @GetMapping(value = "/clients")
    public ResponseEntity<List<ClientDTO>> getClientsDTO() {
        return new ResponseEntity<>(clientService.getClients(), OK);
    }

    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<ClientDTO> getClientDTO(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(clientService.getClient(id), OK);
    }

    @GetMapping(value = "/clients/current")
    public ResponseEntity<ClientDTO> getAuthenticatedUserDetails(Authentication authentication) {
        return new ResponseEntity<>(clientService.getAuthenticatedUserDetails(authentication), OK);
    }

    @PostMapping(path = "/clients")
    public ResponseEntity<?> register(@RequestParam String firstName, @RequestParam String lastName,
                                  @RequestParam String email, @RequestParam String password) throws IllegalArgumentException {
        try {
            clientService.register(firstName, lastName, email, password);
            return new ResponseEntity<>(CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), FORBIDDEN);
        }
    }

    @DeleteMapping(value = "/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable(name = "id") Long id) {
        clientService.delete(id);
        return new ResponseEntity<>(id, NO_CONTENT);
    }
}
