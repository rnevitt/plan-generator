package com.robertnevitt.plangenerator.calculation;

import java.time.ZonedDateTime;

/**
 * Object to handle amortized loan payments.  Object could probably
 * inherit from a LoanPayment object with many of these fields moved there,
 * but that felt like over engineering. 
 * 
 * @author Robert Nevitt
 *
 */
public class AmortizedLoanPayment {
    public final float borrowerPaymentAmount;
    public final ZonedDateTime datePaymentToBeMade;
    public final float initialOutstandingPrincipal;
    public final float interestToBePaid;
    public final float principalToBePaid;
    public final float remainingOutstandingPrincipal;
    
    private AmortizedLoanPayment(float borrowerPaymentAmount, ZonedDateTime datePaymentToBeMade,
            float initialOutstandingPrincipal, float interestToBePaid, float principalToBePaid,
            float remainingOustandingPrincipal) {
        this.borrowerPaymentAmount = borrowerPaymentAmount;
        this.datePaymentToBeMade = datePaymentToBeMade;
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        this.interestToBePaid = interestToBePaid;
        this.principalToBePaid = principalToBePaid;
        this.remainingOutstandingPrincipal = remainingOustandingPrincipal;
    }
    
    public static AmortizedLoanPayment getLoanPayment(float borrowerPaymentAmount, ZonedDateTime datePaymentToBeMade,
            float initialOutstandingPrincipal,float interestToBePaid, float principalToBePaid,
            float remainingOustandingPrincipal) {
        return new AmortizedLoanPayment( borrowerPaymentAmount, datePaymentToBeMade, initialOutstandingPrincipal, interestToBePaid,
                principalToBePaid, remainingOustandingPrincipal);
    }
    
    @Override
    public String toString() {
        return ("SimpleLoanPayment borrowerPaymentAmount="+borrowerPaymentAmount+", date="+datePaymentToBeMade
                +", initialOutstandingPrincipal="+initialOutstandingPrincipal+", interestPaid="
                +interestToBePaid+", principalPaid="+principalToBePaid+", remainingOutstandingPrincipal="
                +remainingOutstandingPrincipal);
    }
}
