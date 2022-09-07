package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.exceptions.InvalidParameterException;
import com.mindhub.homebanking.service.abstraction.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/api")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping(value = "/transactions")
    public ResponseEntity<?> transaction(@RequestParam Double amount, @RequestParam String description,
                                         @RequestParam String accountOriginNumber,
                                         @RequestParam String accountDestinationNumber,
                                         Authentication authentication) throws InvalidParameterException {
            transactionService.handleTransaction(amount, description, accountOriginNumber,
                    accountDestinationNumber, authentication);
            return new ResponseEntity<>(OK);
    }
}
