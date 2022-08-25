package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.LoanType;

import java.util.List;

public class LoanDTO {

    private Long id;
    private LoanType name;
    private Double maxAmount;
    private List<Integer> payments;

    public LoanDTO() {
    }

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public LoanType getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
