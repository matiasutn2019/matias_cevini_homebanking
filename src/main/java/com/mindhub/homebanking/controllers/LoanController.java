package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.common.DocumentationMessages;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import com.mindhub.homebanking.service.abstraction.ILoanService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(
            value = DocumentationMessages.LOAN_CONTROLLER_GET,
            notes = DocumentationMessages.LOAN_CONTROLLER_GET_DESCRIPTION,
            response = ResponseEntity.class
    )
    @GetMapping(value = "/loans")
    public ResponseEntity<List<?>> getLoans() {
        return new ResponseEntity<>(loanService.getAll(), OK);
    }

    @ApiOperation(
            value = DocumentationMessages.LOAN_CONTROLLER_CREATE,
            notes = DocumentationMessages.LOAN_CONTROLLER_CREATE_DESCRIPTION,
            response = ResponseEntity.class
    )
    @PostMapping(value = "/loans")
    public ResponseEntity<?> loanApply(@RequestBody LoanApplicationDTO loanApplication,
                                                   Authentication authentication) throws InvalidParameterException {
            return new ResponseEntity<>(loanService.loanApply(loanApplication, authentication), CREATED);
    }
}
