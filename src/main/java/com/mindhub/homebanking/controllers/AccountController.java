package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.common.DocumentationMessages;
import com.mindhub.homebanking.exceptions.AccountLimitException;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.service.abstraction.IAccountService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(
            value = DocumentationMessages.ACCOUNT_CONTROLLER_ADMIN_GET,
            notes = DocumentationMessages.ACCOUNT_CONTROLLER_ADMIN_GET_DESCRIPTION,
            response = ResponseEntity.class
    )
    @GetMapping(value = "/admin/accounts")
    public ResponseEntity<List<AccountDTO>> accountList() {
        return new ResponseEntity<>(accountService.accountList(), OK);
    }

    @ApiOperation(
            value = DocumentationMessages.ACCOUNT_CONTROLLER_ADMIN_ID,
            notes = DocumentationMessages.ACCOUNT_CONTROLLER_ADMIN_ID_DESCRIPTION,
            response = ResponseEntity.class
    )
    @GetMapping(value = "/admin/accounts/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.getById(id), OK);
    }

    @ApiOperation(
            value = DocumentationMessages.ACCOUNT_CONTROLLER_CREATE,
            notes = DocumentationMessages.ACCOUNT_CONTROLLER_CREATE_DESCRIPTION,
            response = ResponseEntity.class
    )
    @PostMapping(value = "/clients/current/accounts")
    public ResponseEntity<?> createAccount(Authentication authentication, AccountType accountType)
            throws AccountLimitException {
            accountService.createAccount(authentication, accountType);
            return new ResponseEntity<>(CREATED);
    }

    @ApiOperation(
            value = DocumentationMessages.ACCOUNT_CONTROLLER_GET,
            notes = DocumentationMessages.ACCOUNT_CONTROLLER_GET_DESCRIPTION,
            response = ResponseEntity.class
    )
    @GetMapping(value = "/clients/current/accounts")
    public ResponseEntity<List<AccountDTO>> getAccounts(Authentication authentication) {
        return new ResponseEntity<>(accountService.getAccounts(authentication), OK);
    }

    @ApiOperation(
            value = DocumentationMessages.ACCOUNT_CONTROLLER_TYPES,
            notes = DocumentationMessages.ACCOUNT_CONTROLLER_TYPES_DESCRIPTION,
            response = ResponseEntity.class
    )
    @GetMapping(value = "/clients/current/accounts/types")
    public ResponseEntity<List<String>> getTypes() {
        return new ResponseEntity<>(accountService.getTypes(), OK);
    }

    @ApiOperation(
            value = DocumentationMessages.ACCOUNT_CONTROLLER_DELETE,
            notes = DocumentationMessages.ACCOUNT_CONTROLLER_DELETE_DESCRIPTION,
            response = ResponseEntity.class
    )
    @PatchMapping(value = "/clients/current/accounts")
    public ResponseEntity<?> deleteAccount(@RequestParam String number, Authentication authentication)
            throws InvalidParameterException {
        accountService.deleteAccount(number, authentication);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
