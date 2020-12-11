package com.robertnevitt.plangenerator.calculation;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SimpleLoan extends Loan {

    private final int duration;
    private final LocalDateTime startDate;
    
    private SimpleLoan(float principal, float nominalInterestRate, Period interestRatePeriod, Period compoundingPeriod,
            int duration, LocalDateTime startDate) {
        super(principal, nominalInterestRate, interestRatePeriod, compoundingPeriod);
        this.startDate = startDate;
        this.duration = duration;
    }
    
    public static SimpleLoan getInstance(float principle, float nominalInterestRate, Period interestRatePeriod,
            Period compoundingPeriod, int duration, LocalDateTime startDate) {
        return new SimpleLoan(principle, nominalInterestRate, interestRatePeriod,
                compoundingPeriod, duration, startDate);      
    }
    
    protected float calculateAnnuity() {
        float ratePerPeriod = calculateRatePerPeriod();
        return (ratePerPeriod*this.originalPrincipal)/(1-(float)Math.pow(1+ratePerPeriod,(0f-this.duration)));
    }
    
    protected float calculateRatePerPeriod() {
        return ((this.nominalInterestRate/100f)/(this.interestRatePeriod.numberOfDays/this.compoundingPeriod.numberOfDays));
    }
    
    protected float checkAndAdjustAnnuityForOverpayment(float annuity, float interestToBePaid, float remainingPrincipal) {
        if (annuity > (remainingPrincipal + interestToBePaid)) {
            return remainingPrincipal + interestToBePaid;
        } else {
            return annuity;
        }
    }
    
    protected SimpleLoanPayment generateBorrowerPayment(float annuity, SimpleLoanPayment priorPayment) {
        float interestPaid = calculatInterestPayment(this, priorPayment.remainingOustandingPrincipal);
        annuity = checkAndAdjustAnnuityForOverpayment(annuity, interestPaid, priorPayment.remainingOustandingPrincipal);
        float principalPaid = annuity - interestPaid;
        LocalDateTime paymentDate = LocalDateTime.now();
        float originalPrincipal = priorPayment.remainingOustandingPrincipal;
        float remainingPrincipalAfterPayment = originalPrincipal - principalPaid;
        
        return SimpleLoanPayment.getLoanPayment(annuity, paymentDate, originalPrincipal, 
                interestPaid, principalPaid, remainingPrincipalAfterPayment);
    }
    
    public ArrayList<SimpleLoanPayment> generateAllBorrowerPayments() {
        System.out.println(this.toString());
        ArrayList<SimpleLoanPayment> payments = new ArrayList<SimpleLoanPayment>();
        float annuity = calculateAnnuity();
        float interestToBePaid = calculatInterestPayment(this, this.originalPrincipal);
        annuity = checkAndAdjustAnnuityForOverpayment(annuity, interestToBePaid, this.originalPrincipal);
        float principalToBePaid = annuity - interestToBePaid;
        float remainingOutstandingPrincipal =  this.originalPrincipal - principalToBePaid;

        SimpleLoanPayment payment = SimpleLoanPayment.getLoanPayment(annuity, LocalDateTime.now(), this.originalPrincipal, 
                calculatInterestPayment(this, this.originalPrincipal), principalToBePaid, remainingOutstandingPrincipal);
        payments.add(payment);
   
        while (payment.remainingOustandingPrincipal > 0.01f) {
            payment = generateBorrowerPayment(annuity, payment);
            payments.add(payment);

        }
              
        return payments;
    }
    
    @Override
    public String toString() {
        return ("SimpleLoan principal="+originalPrincipal+", nominalInterestRate="+nominalInterestRate
                +", interestRatePeriod="+interestRatePeriod
                +", compoundingPeriod="+compoundingPeriod
                +", duration="+duration+", startDate="+startDate);
    }
    
    
    

}
