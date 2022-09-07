package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.service.abstraction.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDTO>> accountDTOList() {
        return new ResponseEntity<>(accountService.accountList(), OK);
    }

    @GetMapping(value = "/accounts/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.getById(id), OK);
    }

    @PostMapping(value = "/clients/current/accounts")
    public ResponseEntity<?> createAccount(Authentication authentication) throws Exception {
            accountService.createAccount(authentication);
            return new ResponseEntity<>(CREATED);
    }

    @GetMapping(value = "/clients/current/accounts")
    public ResponseEntity<List<AccountDTO>> clientAccounts(Authentication authentication) {
        return new ResponseEntity<>(accountService.getAccounts(authentication), OK);
    }
}
