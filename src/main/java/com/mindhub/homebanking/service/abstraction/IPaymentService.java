package com.mindhub.homebanking.service.abstraction;

import com.mindhub.homebanking.DTO.PaymentDTO;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import org.springframework.security.core.Authentication;

public interface IPaymentService {

    void handlePayment(Authentication authentication, PaymentDTO payment) throws InvalidParameterException;
}
