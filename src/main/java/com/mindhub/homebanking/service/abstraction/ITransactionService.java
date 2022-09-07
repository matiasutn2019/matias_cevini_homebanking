package com.mindhub.homebanking.service.abstraction;

import com.mindhub.homebanking.exceptions.InvalidParameterException;
import org.springframework.security.core.Authentication;

public interface ITransactionService {

    void handleTransaction(Double amount, String description,
                     String accountOriginNumber, String accountDestinationNumber,
                     Authentication authentication) throws InvalidParameterException;
}
