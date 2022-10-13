package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.common.messages.DocumentationMessages;
import com.mindhub.homebanking.exceptions.EmailAlreadyExistException;
import com.mindhub.homebanking.exceptions.InvalidCredentialsException;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import com.mindhub.homebanking.exceptions.SendEmailException;
import com.mindhub.homebanking.service.abstraction.IClientService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(
            value = DocumentationMessages.CLIENT_CONTROLLER_ADMIN_GET,
            notes = DocumentationMessages.CLIENT_CONTROLLER_ADMIN_GET_DESCRIPTION,
            response = ResponseEntity.class
    )
    @GetMapping(value = "/admin/clients")
    public ResponseEntity<List<ClientDTO>> getClients() {
        return new ResponseEntity<>(clientService.getClients(), OK);
    }

    @ApiOperation(
            value = DocumentationMessages.CLIENT_CONTROLLER_DETAILS,
            notes = DocumentationMessages.CLIENT_CONTROLLER_DETAILS_DESCRIPTION,
            response = ResponseEntity.class
    )
    @GetMapping(value = "/clients/current")
    public ResponseEntity<ClientDTO> getAuthenticatedUserDetails(Authentication authentication) {
        return new ResponseEntity<>(clientService.getAuthenticatedUserDetails(authentication), OK);
    }

    @ApiOperation(
            value = DocumentationMessages.CLIENT_CONTROLLER_CREATE,
            notes = DocumentationMessages.CLIENT_CONTROLLER_CREATE_DESCRIPTION,
            response = ResponseEntity.class
    )
    @PostMapping(path = "/clients")
    public ResponseEntity<?> register(@RequestParam String firstName, @RequestParam String lastName,
                                  @RequestParam String email, @RequestParam String password)
            throws EmailAlreadyExistException, InvalidCredentialsException, SendEmailException {
            clientService.register(firstName, lastName, email, password);
            return new ResponseEntity<>(CREATED);
    }

    @ApiOperation(
            value = DocumentationMessages.CLIENT_CONTROLLER_ADMIN_DELETE,
            notes = DocumentationMessages.CLIENT_CONTROLLER_ADMIN_DELETE_DESCRIPTION,
            response = ResponseEntity.class
    )
    @DeleteMapping(value = "/admin/clients")
    public ResponseEntity<?> deleteClient(@RequestParam Long id) throws InvalidParameterException {
        clientService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}