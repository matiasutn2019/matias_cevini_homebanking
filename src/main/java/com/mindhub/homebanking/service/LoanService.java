package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.ClientLoanDTO;
import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.service.abstraction.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static java.util.stream.Collectors.toList;

@Service
public class LoanService implements ILoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<LoanDTO> getAll() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
    }

    @Transactional
    @Override
    public ClientLoanDTO loanApply(LoanApplicationDTO loanApplication,
                                   Authentication authentication) {
        Optional<Client> client = clientRepository.findByEmail(authentication.getName());
        Optional<Loan> loan = loanRepository.findById(loanApplication.getId());
        Optional<Account> accountDestination = accountRepository.findByNumber(loanApplication.getAccountNumber());
        validations(loanApplication, loan.get(), accountDestination.get(), client.get());
        return new ClientLoanDTO(createClientLoan(loanApplication, client.get(), loan.get(), accountDestination.get()));
    }

    private ClientLoan createClientLoan(LoanApplicationDTO loanApplication, Client client, Loan loan, Account account) {
        Double amountPlusTax = loanApplication.getAmount() + (loanApplication.getAmount() * 0.2);
        ClientLoan clientLoan = new ClientLoan(amountPlusTax, loanApplication.getPayments(), client, loan);
        //Se debe crear una solicitud de préstamo con el monto solicitado sumando el 20% del mismo

        Transaction transaction = new Transaction(CREDIT, LocalDateTime.now(), loanApplication.getAmount(),
                loan.getName() + " loan approved");
        account.addTransaction(transaction);
        //Se debe crear una transacción “CREDIT” asociada a la cuenta de destino (el monto debe quedar positivo)
        //con la descripción concatenando el nombre del préstamo y la frase “loan approved”

        account.setBalance(account.getBalance() + loanApplication.getAmount());
        //Se debe actualizar la cuenta de destino sumando el monto solicitado.

        clientLoanRepository.save(clientLoan);
        transactionRepository.save(transaction);

        return clientLoan;
    }

    private void validations(LoanApplicationDTO loanApplication, Loan loan,
                             Account accountDestination, Client client) {
        if (loanApplication.getId() == null || loanApplication.getAmount() == null ||
            loanApplication.getPayments() == null || loanApplication.getAccountNumber().isEmpty()) {
            throw new IllegalArgumentException("One or more of the parameters is empty");
            //Verificar que los datos sean correctos, es decir no estén vacíos.
        }

        if (loanApplication.getAmount() <= 0 || loanApplication.getPayments() <= 0) {
            throw new IllegalArgumentException("The parameter is <= 0");
            //que el monto no sea 0 o que las cuotas no sean 0
        }

        if (loan == null) {
            throw new IllegalArgumentException("The chosen loan does not exist");
            //Verificar que el préstamo exista
        }

        if (loanApplication.getAmount() > loan.getMaxAmount()) {
            throw new IllegalArgumentException("The requested amount exceeds the maximum loan amount");
            //Verificar que el monto solicitado no exceda el monto máximo del préstamo
        }

        if (!loan.getPayments().contains(loanApplication.getPayments())) {
            throw new IllegalArgumentException("The number of payments is not available");
            //Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo
        }

        if (accountDestination == null) {
            throw new IllegalArgumentException("The account does not exist");
            //Verificar que la cuenta de destino exista
        }

        if (!client.getAccounts().contains(accountDestination)) {
            throw new IllegalArgumentException("The account does not belong to the client");
            //Verificar que la cuenta de destino pertenezca al cliente autenticado
        }
    }
}