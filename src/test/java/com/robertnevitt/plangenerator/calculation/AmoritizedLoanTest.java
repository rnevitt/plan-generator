package com.robertnevitt.plangenerator.calculation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AmoritizedLoanTest {
    private static AmortizedLoan loan;
    
    @BeforeAll
    public static void buildAmortiedLoanObject() {
        loan = AmortizedLoan.getInstance(5000f, 5.0f, Period.YEARLY, Period.MONTHLY,
                24, ZonedDateTime.parse("2018-01-01T00:00:00Z"));
    }
    
    @Test
    public void calculateAnnuityTest() throws Exception{
        float annuity = loan.calculateAnnuity();
        assertEquals(219.35420222705078f,annuity);
    }
    
    @Test
    public void roundCentTest() throws Exception {
        float roundedFloat = AmortizedLoan.roundCents(4.58323456f);
        assertEquals(4.59f, roundedFloat);
    }
    
    @Test
    public void calculateInitialPaymentTest() {
        AmortizedLoanPayment loanPayment = loan.calculateInitialPayment();
        assertEquals(219.36f, loanPayment.borrowerPaymentAmount);
        assertEquals(ZonedDateTime.parse("2018-01-01T00:00:00Z"), loanPayment.datePaymentToBeMade);
        assertEquals(5000.00f, loanPayment.initialOutstandingPrincipal);
        assertEquals("20.83", String.format("%.2f",loanPayment.interestToBePaid));
        assertEquals("198.53", String.format("%.2f",loanPayment.principalToBePaid));
        assertEquals("4801.47", String.format("%.2f",loanPayment.remainingOutstandingPrincipal));
    }
    
    @Test
    public void generateSecondBorrowerPaymentTest() {
        //setup
        float annuity = loan.calculateAnnuity();
        AmortizedLoanPayment loanPayment = loan.calculateInitialPayment();
        
        //object under test
        AmortizedLoanPayment payment = loan.generateBorrowerPayment(annuity, loanPayment);
        assertEquals(219.36f, AmortizedLoan.roundCents(payment.borrowerPaymentAmount));
        assertEquals(ZonedDateTime.parse("2018-02-01T00:00:00Z"), payment.datePaymentToBeMade);
        assertEquals("4801.47", String.format("%.2f",payment.initialOutstandingPrincipal));
        assertEquals("20.01", String.format("%.2f",payment.interestToBePaid));
        assertEquals("199.35", String.format("%.2f",payment.principalToBePaid));
        assertEquals("4602.13", String.format("%.2f",payment.remainingOutstandingPrincipal));        
    }
    
    @Test
    public void generateAllBorrowerPaymentsTest() {
        ArrayList<AmortizedLoanPayment> payments = loan.generateAllBorrowerPayments();
        assertEquals(24, payments.size());
    }
    
    @Test
    public void checkAndAdustAnnityForOverpayment() {
        float annuityReduced = loan.checkAndAdjustAnnuityForOverpayment(500f, 100f, 300f);
        assertEquals(400f,AmortizedLoan.roundCents(annuityReduced));      
    }
}
