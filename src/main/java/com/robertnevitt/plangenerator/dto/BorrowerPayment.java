package com.robertnevitt.plangenerator.dto;

/**
 * @author Robert Nevitt
 *
 */
public class BorrowerPayment {

    private final String borrowerPaymentAmount;
    private final String date;
    private final String initialOutstandingPrincipal;
    private final String interest;
    private final String principal;
    private final String remainingOutstandingPrincipal;

    public static class Builder {
        //required parameters
        private final String borrowerPaymentAmount;
        private final String date;
        private final String initialOutstandingPrincipal;
        private final String interest;
        private final String principal;
        private final String remainingOutstandingPrincipal;
        
        public Builder(String borrowerPaymentAmount, String date, String initialOutstandingPrincipal,
                String interest, String principal, String remainingOutstandingPrincipal) {
           this.borrowerPaymentAmount = borrowerPaymentAmount;
           this.date = date;
           this.initialOutstandingPrincipal = initialOutstandingPrincipal;
           this.interest = interest;
           this.principal = principal;
           this.remainingOutstandingPrincipal = remainingOutstandingPrincipal;
              
        }
        
        public BorrowerPayment build() {
            return new BorrowerPayment(this);
        }
        
    }
    
    private BorrowerPayment(Builder builder) {
       borrowerPaymentAmount = builder.borrowerPaymentAmount;
       date = builder.date;
       initialOutstandingPrincipal = builder.initialOutstandingPrincipal;
       interest = builder.interest;
       principal = builder.principal;
       remainingOutstandingPrincipal = builder.remainingOutstandingPrincipal;
    }

}
