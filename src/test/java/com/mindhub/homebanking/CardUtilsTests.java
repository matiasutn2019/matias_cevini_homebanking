package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void cardNumberLength() {
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber, hasLength(19));
    }

    @Test
    public void cvvNumberIsCreated(){
        String cvvNumber = CardUtils.getCVV();
        assertThat(cvvNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void cvvNumberLength() {
        String cvvNumber = CardUtils.getCVV();
        assertThat(cvvNumber, hasLength(3));
    }
}