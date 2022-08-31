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

import static com.mindhub.homebanking.models.LoanType.*;

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
            Client cliente1 = new Client("Lorenzo", "Melba", "melba@gmail.com", passwordEncoder.encode("pass123"));
            Account cuenta1 = new Account("VIN-001", LocalDate.now(), 5000.0);
            Account cuenta2 = new Account("VIN-002", LocalDate.now().plusDays(1l), 7500.0);
            cliente1.addAccount(cuenta1);
            cliente1.addAccount(cuenta2);
            Transaction t1 = new Transaction(TransactionType.CREDIT, LocalDate.now(),
                    1000.0, "crédito de $ 1000 a la cuenta 1");
            Transaction t2 = new Transaction(TransactionType.DEBIT, LocalDate.now(),
                    -500.0, "débito de $ 500 a la cuenta 1");
            Transaction t3 = new Transaction(TransactionType.CREDIT, LocalDate.now(),
                    1000.0, "crédito de $ 1000 a la cuenta 2");
            Transaction t4 = new Transaction(TransactionType.DEBIT, LocalDate.now(),
                    -500.0, "débito de $ 500 a la cuenta 2");
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

            Client cliente2 = new Client("Juan", "Garcia", "juancho@gmail.com", passwordEncoder.encode("pass456"));
            Account cuenta3 = new Account("VIN-003", LocalDate.now(), 5000.0);
            Account cuenta4 = new Account("VIN-004", LocalDate.now().plusDays(1l), 7500.0);
            cliente2.addAccount(cuenta3);
            cliente2.addAccount(cuenta4);
            Transaction t5 = new Transaction(TransactionType.CREDIT, LocalDate.now(),
                    1000.0, "crédito de $ 1000 a la cuenta 3");
            Transaction t6 = new Transaction(TransactionType.DEBIT, LocalDate.now(),
                    -500.0, "débito de $ 500 a la cuenta 3");
            Transaction t7 = new Transaction(TransactionType.CREDIT, LocalDate.now(),
                    1000.0, "crédito de $ 1000 a la cuenta 4");
            Transaction t8 = new Transaction(TransactionType.DEBIT, LocalDate.now(),
                    -500.0, "débito de $ 500 a la cuenta 4");
            cuenta3.addTransaction(t5);
            cuenta3.addTransaction(t6);
            cuenta4.addTransaction(t7);
            cuenta4.addTransaction(t8);
            clientRepository.save(cliente2);
            accountRepository.save(cuenta3);
            accountRepository.save(cuenta4);
            transactionRepository.save(t5);
            transactionRepository.save(t6);
            transactionRepository.save(t7);
            transactionRepository.save(t8);

            Loan l1 = new Loan(HIPOTECARIO, 500000.0, List.of(12,24,36,48,60));
            Loan l2 = new Loan(PERSONAL, 100000.0, List.of(6,12,24));
            Loan l3 = new Loan(AUTOMOTRIZ, 300000.0, List.of(6,12,24,36));
            loanRepository.save(l1);
            loanRepository.save(l2);
            loanRepository.save(l3);

            ClientLoan clientLoan1 = new ClientLoan(400000.0, 60, cliente1, l1);
            ClientLoan clientLoan2 = new ClientLoan(50000.0, 12, cliente1, l2);
            ClientLoan clientLoan3 = new ClientLoan(100000.0, 24, cliente2, l2);
            ClientLoan clientLoan4 = new ClientLoan(200000.0, 36, cliente2, l3);
            clientLoanRepository.save(clientLoan1);
            clientLoanRepository.save(clientLoan2);
            clientLoanRepository.save(clientLoan3);
            clientLoanRepository.save(clientLoan4);

            String cardHolder1 = cliente1.getFirstName() + cliente1.getLastName();
            String cardHolder2 = cliente2.getFirstName() + cliente2.getLastName();
            Card card1 = new Card(CardType.DEBIT, "0000 1111 2222 3333", "123", LocalDate.now(), LocalDate.now().plusYears(5),
                    cardHolder1,CardColor.GOLD);
            Card card2 = new Card(CardType.CREDIT, "4444 5555 6666 7777", "456", LocalDate.now(), LocalDate.now().plusYears(5),
                    cardHolder1, CardColor.PLATINUM);
            Card card3 = new Card(CardType.CREDIT, "8888 9999 0000 1111", "789", LocalDate.now(), LocalDate.now().plusYears(5),
                    cardHolder2, CardColor.SILVER);
            Card card5 = new Card(CardType.CREDIT, "0000 1111 5555 4444", "730", LocalDate.now(), LocalDate.now().plusYears(5),
                    cardHolder1,CardColor.SILVER);
            cliente1.addCard(card1);
            cliente1.addCard(card2);
            cliente1.addCard(card5);
            cliente2.addCard(card3);
            cardRepository.save(card1);
            cardRepository.save(card2);
            cardRepository.save(card3);
            cardRepository.save(card5);

            Client admin = new Client("Admin", "Rodriguez",
                    "admin@gmail.com", passwordEncoder.encode("admin123"));
            clientRepository.save(admin);

        };
    }
}
