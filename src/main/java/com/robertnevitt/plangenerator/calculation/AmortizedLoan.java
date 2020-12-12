package com.robertnevitt.plangenerator.calculation;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.robertnevitt.plangenerator.controller.PlanGeneratorController;

public class AmortizedLoan extends Loan {

    private static final Logger logger = LogManager.getLogger(AmortizedLoan.class);
    
    private final int duration;
    private final ZonedDateTime startDate;
    
    private AmortizedLoan(float principal, float nominalInterestRate, Period interestRatePeriod, Period compoundingPeriod,
            int duration, ZonedDateTime startDate) {
        super(principal, nominalInterestRate, interestRatePeriod, compoundingPeriod);
        this.startDate = startDate;
        this.duration = duration;
        logger.debug("SimpleLoan(...): startDate: " + startDate + "   duration:" + duration);
    }
    
    public static AmortizedLoan getInstance(float principle, float nominalInterestRate, Period interestRatePeriod,
            Period compoundingPeriod, int duration, ZonedDateTime startDate) {
        return new AmortizedLoan(principle, nominalInterestRate, interestRatePeriod,
                compoundingPeriod, duration, startDate);      
    }
    
    protected float calculateAnnuity() {
        float ratePerPeriod = calculateRatePerPeriod();
        return (ratePerPeriod*this.originalPrincipal)/(1-(float)Math.pow(1+ratePerPeriod,(0f-this.duration)));
    }
    
    protected float checkAndAdjustAnnuityForOverpayment(float annuity, float interestToBePaid, float remainingPrincipal) {
        if (annuity > (remainingPrincipal + interestToBePaid)) {
            return remainingPrincipal + interestToBePaid;
        } else {
            return annuity;
        }
    }
    
    protected AmortizedLoanPayment generateBorrowerPayment(float annuity, AmortizedLoanPayment priorPayment) {
        float interestPaid = calculatInterestPayment(this, priorPayment.remainingOutstandingPrincipal);
        annuity = checkAndAdjustAnnuityForOverpayment(annuity, interestPaid, priorPayment.remainingOutstandingPrincipal);
        float principalPaid = annuity - interestPaid;
        ZonedDateTime paymentDate = priorPayment.datePaymentToBeMade.plusMonths(1);
        float originalPrincipal = priorPayment.remainingOutstandingPrincipal;
        float remainingPrincipalAfterPayment = originalPrincipal - principalPaid;
        
        return AmortizedLoanPayment.getLoanPayment(annuity, paymentDate, originalPrincipal, 
                interestPaid, principalPaid, remainingPrincipalAfterPayment);
    }
    
    public ArrayList<AmortizedLoanPayment> generateAllBorrowerPayments() {
        logger.debug(this.toString());
        ArrayList<AmortizedLoanPayment> payments = new ArrayList<AmortizedLoanPayment>();
        AmortizedLoanPayment payment = calculateInitialPayment();
        payments.add(calculateInitialPayment());
        
        //Generating all remaining months.
        while (payment.remainingOutstandingPrincipal > 0.01f) {
            payment = generateBorrowerPayment(payment.borrowerPaymentAmount, payment);
            payments.add(payment);
        }
              
        return payments;
    }
    
    //Because initial payment requires additional calculations I have created
    //a separate method to  handle this.
    protected AmortizedLoanPayment calculateInitialPayment() {
        float annuity = roundCents(calculateAnnuity());
        float interestToBePaid = calculatInterestPayment(this, this.originalPrincipal);
        annuity = checkAndAdjustAnnuityForOverpayment(annuity, interestToBePaid, this.originalPrincipal);
        float principalToBePaid = annuity - interestToBePaid;
        float remainingOutstandingPrincipal =  this.originalPrincipal - principalToBePaid;

        //Establishing first month payment. 
        return AmortizedLoanPayment.getLoanPayment(annuity, startDate, this.originalPrincipal, 
                calculatInterestPayment(this, this.originalPrincipal), principalToBePaid, remainingOutstandingPrincipal);
    }
    
    protected float roundCents(float numberToBeRounded) {
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
