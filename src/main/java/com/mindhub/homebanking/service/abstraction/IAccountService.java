package com.mindhub.homebanking.service.abstraction;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.exceptions.AccountLimitException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IAccountService {

    List<AccountDTO> accountList();
    AccountDTO getById(Long id);
    void createAccount(Authentication authentication) throws AccountLimitException;

    List<AccountDTO> getAccounts(Authentication authentication);
}
