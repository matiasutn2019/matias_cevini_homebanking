package com.mindhub.homebanking.service.abstraction;

import com.mindhub.homebanking.DTO.ClientLoanDTO;
import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ILoanService {
    List<LoanDTO> getAll();

    ClientLoanDTO loanApply(LoanApplicationDTO loanApplication,
                            Authentication authentication) throws InvalidParameterException;
}
