package com.robertnevitt.plangenerator.calculation;

import java.time.LocalDateTime;

public class SimpleLoanPayment {
    public final float borrowerPaymentAmount;
    public final LocalDateTime date;
    public final float initialOutstandingPrincipal;
    public final float interestPaid;
    public final float principalPaid;
    public final float remainingOustandingPrincipal;
    
    private SimpleLoanPayment(float borrowerPaymentAmount, LocalDateTime date,
            float initialOutstandingPrincipal, float interestPaid, float principalPaid,
            float remainingOustandingPrincipal) {
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        this.date = date;
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        this.interestPaid = interestPaid;
        this.principalPaid = principalPaid;
        this.remainingOustandingPrincipal = remainingOustandingPrincipal;
    }
    
    public static SimpleLoanPayment getLoanPayment(float borrowerPaymentAmount, LocalDateTime date,
            float initialOutstandingPrincipal,float interestPaid, float principalPaid,
            float remainingOustandingPrincipal) {
        return new SimpleLoanPayment( borrowerPaymentAmount, date, initialOutstandingPrincipal, interestPaid,
                principalPaid, remainingOustandingPrincipal);
    }
    
    @Override
    public String toString() {
        return ("SimpleLoanPayment borrowerPaymentAmount="+borrowerPaymentAmount+", date="+date
                +", initialOutstandingPrincipal="+initialOutstandingPrincipal+", interestPaid="
                +interestPaid+", principalPaid="+principalPaid+", remainingOutstandingPrincipal="
                +remainingOustandingPrincipal);
    }
}
