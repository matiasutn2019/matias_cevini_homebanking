package com.mindhub.homebanking.service.abstraction;

import com.mindhub.homebanking.DTO.PaymentDTO;
import com.mindhub.homebanking.exceptions.InvalidParameterException;

public interface IPaymentService {

    void handlePayment(PaymentDTO payment) throws InvalidParameterException;
}
