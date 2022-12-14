package com.mindhub.homebanking.service.abstraction;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.exceptions.AccountLimitException;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import com.mindhub.homebanking.models.AccountType;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IAccountService {
    List<AccountDTO> accountList();
    AccountDTO getById(Long id);
    void createAccount(Authentication authentication, AccountType accountType) throws AccountLimitException;

    List<AccountDTO> getAccounts(Authentication authentication);

    void deleteAccount(String number, Authentication authentication) throws InvalidParameterException;

    List<String> getTypes();
}
