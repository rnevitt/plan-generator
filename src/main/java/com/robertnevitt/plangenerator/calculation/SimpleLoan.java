package com.robertnevitt.plangenerator.calculation;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.robertnevitt.plangenerator.dto.BorrowerPayment;

public class SimpleLoan extends Loan {

    private final int duration;
    private final LocalDateTime startDate;
    
    private SimpleLoan(float principle, float nominalInterestRate, Period interestRatePeriod, Period compoundingPeriod, int duration, LocalDateTime startDate) {
        super(principle, nominalInterestRate, interestRatePeriod, compoundingPeriod);
        this.startDate = startDate;
        this.duration = duration;
    }
    
    public static SimpleLoan getInstance(float principle, float nominalInterestRate, Period interestRatePeriod, Period compoundingPeriod, int duration, LocalDateTime startDate) {
        return new SimpleLoan(principle, nominalInterestRate, interestRatePeriod, compoundingPeriod, duration, startDate);      
    }

    @Override
    protected float calculatePaymentAppliedToPrinciple() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    protected float calculateAnnuity() {
        float ratePerPeriod = calculateRatePerPeriod();
        return (ratePerPeriod*this.principle)/(1-(float)Math.pow(1+ratePerPeriod,this.duration));
    }
    
    protected float calculateRatePerPeriod() {
        return (this.interestRatePeriod.numberOfDays/this.compoundingPeriod.numberOfDays);
    }
 
    protected SimpleLoanPayment generateBorrowerPayment(float annuity, SimpleLoanPayment payment) {
        
        return SimpleLoanPayment.getLoanPayment(annuity);
    }
    
    public ArrayList<SimpleLoanPayment> generateAllBorrowerPayments() {
        float annuity = calculateAnnuity();
        SimpleLoanPayment payment = generateBorrowerPayment(annuity);
        while (payment.initialOutstandingPrincipal > 0) {
            if (payment.initialOutstandingPrincipal > annuity) {
                
            }
        }
    }
    
    

}
