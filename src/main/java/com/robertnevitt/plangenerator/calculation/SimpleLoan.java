package com.robertnevitt.plangenerator.calculation;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.robertnevitt.plangenerator.controller.PlanGeneratorController;

public class SimpleLoan extends Loan {

    private static final Logger logger = LogManager.getLogger(SimpleLoan.class);
    
    private final int duration;
    private final ZonedDateTime startDate;
    
    private SimpleLoan(float principal, float nominalInterestRate, Period interestRatePeriod, Period compoundingPeriod,
            int duration, ZonedDateTime startDate) {
        super(principal, nominalInterestRate, interestRatePeriod, compoundingPeriod);
        this.startDate = startDate;
        this.duration = duration;
        logger.debug("SimpleLoan(...): startDate: " + startDate + "   duration:" + duration);
    }
    
    public static SimpleLoan getInstance(float principle, float nominalInterestRate, Period interestRatePeriod,
            Period compoundingPeriod, int duration, ZonedDateTime startDate) {
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
        logger.debug(this.toString());
        float interestPaid = calculatInterestPayment(this, priorPayment.remainingOustandingPrincipal);
        annuity = checkAndAdjustAnnuityForOverpayment(annuity, interestPaid, priorPayment.remainingOustandingPrincipal);
        float principalPaid = annuity - interestPaid;
        ZonedDateTime paymentDate = priorPayment.date.plusMonths(1);
        float originalPrincipal = priorPayment.remainingOustandingPrincipal;
        float remainingPrincipalAfterPayment = originalPrincipal - principalPaid;
        
        return SimpleLoanPayment.getLoanPayment(annuity, paymentDate, originalPrincipal, 
                interestPaid, principalPaid, remainingPrincipalAfterPayment);
    }
    
    public ArrayList<SimpleLoanPayment> generateAllBorrowerPayments() {
        logger.debug(this.toString());
        ArrayList<SimpleLoanPayment> payments = new ArrayList<SimpleLoanPayment>();
        SimpleLoanPayment payment = calculateInitialPayment();
        payments.add(calculateInitialPayment());
        
        //Generating all remaining months.
        while (payment.remainingOustandingPrincipal > 0.01f) {
            payment = generateBorrowerPayment(payment.borrowerPaymentAmount, payment);
            payments.add(payment);
        }
              
        return payments;
    }
    
    //Because initial payment requires additional calculations I have created
    //a separate method to  handle this.
    private SimpleLoanPayment calculateInitialPayment() {
        float annuity = roundCents(calculateAnnuity());
        float interestToBePaid = calculatInterestPayment(this, this.originalPrincipal);
        annuity = checkAndAdjustAnnuityForOverpayment(annuity, interestToBePaid, this.originalPrincipal);
        float principalToBePaid = annuity - interestToBePaid;
        float remainingOutstandingPrincipal =  this.originalPrincipal - principalToBePaid;

        //Establishing first month payment. 
        return SimpleLoanPayment.getLoanPayment(annuity, startDate, this.originalPrincipal, 
                calculatInterestPayment(this, this.originalPrincipal), principalToBePaid, remainingOutstandingPrincipal);
    }
    
    private float roundCents(float numberToBeRounded) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return Float.valueOf(df.format(numberToBeRounded));
    }
    
    @Override
    public String toString() {
        return ("SimpleLoan principal="+originalPrincipal+", nominalInterestRate="+nominalInterestRate
                +", interestRatePeriod="+interestRatePeriod
                +", compoundingPeriod="+compoundingPeriod
                +", duration="+duration+", startDate="+startDate);
    }
    
    
    

}
