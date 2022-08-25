package com.mindhub.homebanking.service.abstraction;

import org.springframework.security.core.Authentication;

public interface ITransactionService {

    void transaction(Double amount, String description,
                     String accountOriginNumber, String accountDestinationNumber,
                     Authentication authentication) throws IllegalArgumentException;
}
