package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

    @Autowired
    private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository) {
        return (args) -> {
            Client cliente1 = new Client("Lorenzo", "Melba",
                    "melba@gmail.com", passwordEncoder.encode("pass123"));
            Account cuenta1 = new Account("VIN-001", LocalDate.now(),
                    5000.0, AccountType.AHORRO);
            Account cuenta2 = new Account("VIN-002", LocalDate.now().plusDays(1l),
                    7500.0, AccountType.CORRIENTE);
            cliente1.addAccount(cuenta1);
            cliente1.addAccount(cuenta2);
            Transaction t1 = new Transaction(TransactionType.CREDIT, LocalDate.now(),
                    1000.0, "crédito de $ 1000 a la cuenta 1", 6000.0);
            Transaction t2 = new Transaction(TransactionType.DEBIT, LocalDate.now(),
                    -500.0, "débito de $ 500 a la cuenta 1", 5500.0);
            Transaction t3 = new Transaction(TransactionType.CREDIT, LocalDate.now(),
                    1000.0, "crédito de $ 1000 a la cuenta 2", 8500.0);
            Transaction t4 = new Transaction(TransactionType.DEBIT, LocalDate.now(),
                    -500.0, "débito de $ 500 a la cuenta 2", 8000.0);
            cuenta1.addTransaction(t1);
            cuenta1.addTransaction(t2);
            cuenta2.addTransaction(t3);
            cuenta2.addTransaction(t4);
            clientRepository.save(cliente1);
            accountRepository.save(cuenta1);
            accountRepository.save(cuenta2);
            transactionRepository.save(t1);
            transactionRepository.save(t2);
            transactionRepository.save(t3);
            transactionRepository.save(t4);

            Loan l1 = new Loan("HIPOTECARIO", 500000.0, 10.0, List.of(12,24,36,48,60));
            Loan l2 = new Loan("PERSONAL", 100000.0, 20.0, List.of(6,12,24));
            Loan l3 = new Loan("AUTOMOTRIZ", 300000.0, 30.0, List.of(6,12,24,36));
            loanRepository.save(l1);
            loanRepository.save(l2);
            loanRepository.save(l3);

            ClientLoan clientLoan1 = new ClientLoan(400000.0, 60, cliente1, l1);
            ClientLoan clientLoan2 = new ClientLoan(50000.0, 12, cliente1, l2);
            clientLoanRepository.save(clientLoan1);
            clientLoanRepository.save(clientLoan2);

            String cardHolder = cliente1.getFirstName() + " " + cliente1.getLastName();
            Card card1 = new Card(CardType.DEBIT, "0000 1111 2222 3333", "123",
                    LocalDate.now(), LocalDate.now().plusYears(5),
                    cardHolder,CardColor.GOLD);
            Card card2 = new Card(CardType.CREDIT, "4444 5555 6666 7777", "456",
                    LocalDate.now(), LocalDate.now().plusYears(5),
                    cardHolder, CardColor.PLATINUM);
            Card card5 = new Card(CardType.CREDIT, "0000 1111 5555 4444", "730",
                    LocalDate.now(), LocalDate.now().plusYears(5),
                    cardHolder, CardColor.SILVER);
            cliente1.addCard(card1);
            cliente1.addCard(card2);
            cliente1.addCard(card5);
            cardRepository.save(card1);
            cardRepository.save(card2);
            cardRepository.save(card5);

            Client admin = new Client("Admin", "Admin",
                    "admin@gmail.com", passwordEncoder.encode("admin123"));
            clientRepository.save(admin);

        };
    }
}
