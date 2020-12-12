package com.robertnevitt.plangenerator.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BorrowerPaymentTest {

    @Test
    public void builderTest() throws Exception{
        String borrowerPaymentAmount = "219.36";
        String date = "2018-01-01T00:00:00Z";
        String initialOutstandingPrincipal = "5000.00";
        String interest = "20.83";
        String principal = "198.53";
        String remainingOutstandingPrincipal = "4801.47";
        
        BorrowerPayment.Builder builder = new BorrowerPayment.Builder(borrowerPaymentAmount, date, initialOutstandingPrincipal, interest, principal, remainingOutstandingPrincipal);
        BorrowerPayment payment = builder.build();
        
        assertEquals("219.36", payment.getBorrowerPaymentAmount());
        assertEquals("2018-01-01T00:00:00Z", payment.getDate());
        assertEquals("5000.00", payment.getInitialOutstandingPrincipal());
        assertEquals("20.83", payment.getInterest());
        assertEquals("198.53", payment.getPrincipal());
        assertEquals("4801.47", payment.getRemainingOutstandingPrincipal());
    }
}
