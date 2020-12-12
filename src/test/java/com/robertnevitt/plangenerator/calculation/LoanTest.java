package com.robertnevitt.plangenerator.calculation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoanTest {
    
    private static AmortizedLoan loan;
    
    @BeforeAll
    public static void buildAmortiedLoanObject() {
        loan = AmortizedLoan.getInstance(5000f, 5.0f, Period.YEARLY, Period.MONTHLY,
                24, ZonedDateTime.parse("2018-01-01T00:00:00Z"));
    }
    
    @Test
    public void calculateInterestPaymentTest() {
        float objectUnderTest = Loan.calculateInterestPayment(loan, 5000.00f);
        assertEquals(20.833334, objectUnderTest);
    }
}
