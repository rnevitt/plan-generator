package com.robertnevitt.plangenerator.calculation;

import java.time.LocalDateTime;

public class SimpleLoanPayment {
    public final float borrowerPaymentAmount;
    public final LocalDateTime date;
    public final float initialOutstandingPrinciple;
    public final float interest;
    public final float principleOfLoanBeforePayment;
    public final float remainingOustandingPrinciple;
    
    private SimpleLoanPayment(float borrowerPaymentAmount, LocalDateTime date,
            float initialOutstandingPrinciple, float interest, float principleOfLoanBeforePayment,
            float remainingOustandingPrinciple) {
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        this.date = date;
        this.initialOutstandingPrinciple = initialOutstandingPrinciple;
        this.interest = interest;
        this.principleOfLoanBeforePayment = principleOfLoanBeforePayment;
        this.remainingOustandingPrinciple = remainingOustandingPrinciple;
    }
    
    public SimpleLoanPayment getLoanPayment(float borrowerPaymentAmount, LocalDateTime date,
            float initialOutstandingPrinciple,float interest, float principleOfLoanBeforePayment,
            float remainingOustandingPrinciple) {
        return new SimpleLoanPayment( borrowerPaymentAmount, date, initialOutstandingPrinciple, interest,
                principleOfLoanBeforePayment, remainingOustandingPrinciple);
    }
    
}
