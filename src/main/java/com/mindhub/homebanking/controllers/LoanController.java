package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import com.mindhub.homebanking.service.abstraction.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api")
public class LoanController {

    @Autowired
    private ILoanService loanService;

    @GetMapping(value = "/loans")
    public ResponseEntity<List<?>> getLoans() {
        return new ResponseEntity<>(loanService.getAll(), OK);
    }

    @PostMapping(value = "/loans")
    public ResponseEntity<?> loanApply(@RequestBody LoanApplicationDTO loanApplication,
                                                   Authentication authentication) throws InvalidParameterException {
            return new ResponseEntity<>(loanService.loanApply(loanApplication, authentication), CREATED);
    }
}
