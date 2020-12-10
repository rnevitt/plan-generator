package com.robertnevitt.plangenerator.dto;

/**
 * @author Robert Nevitt
 *
 */
public class BorrowerPayment {

    private final String borrowerPaymentAmount;
    private final String date;
    private final String initialOutstandingPrinciple;
    private final String interest;
    private final String principle;
    private final String remainingOutstandingPrinciple;

    public static class Builder {
        //required parameters
        private final String borrowerPaymentAmount;
        private final String date;
        private final String initialOutstandingPrinciple;
        private final String interest;
        private final String principle;
        private final String remainingOutstandingPrinciple;
        
        public Builder(String borrowerPaymentAmount, String date, String initialOutstandingPrinciple,
                String interest, String principle, String remainingOutstandingPrinciple) {
           this.borrowerPaymentAmount = borrowerPaymentAmount;
           this.date = date;
           this.initialOutstandingPrinciple = initialOutstandingPrinciple;
           this.interest = interest;
           this.principle = principle;
           this.remainingOutstandingPrinciple = remainingOutstandingPrinciple;
              
        }
        
        public BorrowerPayment build() {
            return new BorrowerPayment(this);
        }
        
    }
    
    private BorrowerPayment(Builder builder) {
       borrowerPaymentAmount = builder.borrowerPaymentAmount;
       date = builder.date;
       initialOutstandingPrinciple = builder.initialOutstandingPrinciple;
       interest = builder.interest;
       principle = builder.principle;
       remainingOutstandingPrinciple = builder.remainingOutstandingPrinciple;
    }

}
