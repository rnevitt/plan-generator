package com.robertnevitt.plangenerator.calculation;

import java.time.LocalDateTime;

public class SimpleLoanPayment {
    public final float borrowerPaymentAmount;
    public final LocalDateTime date;
    public final float initialOutstandingPrincipal;
    public final float interest;
    public final float principalOfLoanBeforePayment;
    public final float remainingOustandingPrincipal;
    
    private SimpleLoanPayment(float borrowerPaymentAmount, LocalDateTime date,
            float initialOutstandingPrincipal, float interest, float principalOfLoanBeforePayment,
            float remainingOustandingPrincipal) {
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        this.date = date;
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        this.interest = interest;
        this.principalOfLoanBeforePayment = principalOfLoanBeforePayment;
        this.remainingOustandingPrincipal = remainingOustandingPrincipal;
    }
    
    public SimpleLoanPayment getLoanPayment(float borrowerPaymentAmount, LocalDateTime date,
            float initialOutstandingPrincipal,float interest, float principalOfLoanBeforePayment,
            float remainingOustandingPrincipal) {
        return new SimpleLoanPayment( borrowerPaymentAmount, date, initialOutstandingPrincipal, interest,
                principalOfLoanBeforePayment, remainingOustandingPrincipal);
    }
    
}
