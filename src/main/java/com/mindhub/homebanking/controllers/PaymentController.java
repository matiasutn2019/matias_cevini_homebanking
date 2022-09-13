package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.PaymentDTO;
import com.mindhub.homebanking.exceptions.InvalidParameterException;
import com.mindhub.homebanking.service.abstraction.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/api")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping(value = "/clients/current/payments")
    public ResponseEntity<?> payment(Authentication authentication, @RequestBody PaymentDTO payment) throws InvalidParameterException {
        paymentService.handlePayment(authentication, payment);
        return new ResponseEntity<>(OK);
    }
}
