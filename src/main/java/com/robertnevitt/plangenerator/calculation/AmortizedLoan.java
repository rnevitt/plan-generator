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
    
    /**
     * Calculate the payment that should be made in order to pay off an amortized loan. 
     * 
     * @return float
     */
    protected float calculateAnnuity() {
        float ratePerPeriod = calculateRatePerPeriod();
        return (ratePerPeriod*this.originalPrincipal)/(1-(float)Math.pow(1+ratePerPeriod,(0f-this.duration)));
    }
    
    /**
     * Checks to make sure a payment does not exceed the remaining principle + interest
     * 
     * @param annuity amount to be paid from initial amortization
     * @param interestToBePaid amount of interest to be paid
     * @param remainingPrincipal amount of remaining principal to be paid
     * @return
     */
    protected float checkAndAdjustAnnuityForOverpayment(float annuity, float interestToBePaid, float remainingPrincipal) {
        if (annuity > (remainingPrincipal + interestToBePaid)) {
            return remainingPrincipal + interestToBePaid;
        } else {
            return annuity;
        }
    }
    
    /**
     * Generates a single payment based on the prior payments information.
     * 
     * @param annuity float
     * @param priorPayment AmortizedLoanPayment
     * @return AmortizedLoanPayment
     */
    protected AmortizedLoanPayment generateBorrowerPayment(float annuity, AmortizedLoanPayment priorPayment) {
        float interestPaid = calculateInterestPayment(this, priorPayment.remainingOutstandingPrincipal);
        annuity = checkAndAdjustAnnuityForOverpayment(annuity, interestPaid,
                priorPayment.remainingOutstandingPrincipal);
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
    

    /**
     * An initial payment requires additional calculations so I have created
     * a separate method to  handle this.
     * 
     * @return AmortizedLoanPayment the first payment in a loan payback scheme.
     */
    protected AmortizedLoanPayment calculateInitialPayment() {
        float annuity = roundCents(calculateAnnuity());
        float interestToBePaid = calculateInterestPayment(this, this.originalPrincipal);
        annuity = checkAndAdjustAnnuityForOverpayment(annuity, interestToBePaid, this.originalPrincipal);
        float principalToBePaid = annuity - interestToBePaid;
        float remainingOutstandingPrincipal =  this.originalPrincipal - principalToBePaid;

        //Establishing first month payment. 
        return AmortizedLoanPayment.getLoanPayment(annuity, startDate, this.originalPrincipal, 
                calculateInterestPayment(this, this.originalPrincipal), principalToBePaid, remainingOutstandingPrincipal);
    }
    
    /**
     * Method to round to the ceiling to make payable in currency which use partial
     * units. Ceiling is used to make sure payer is paying enough to meet loan terms
     * without defaulting by a fraction of a cent.
     * 
     * @param numberToBeRounded
     * @return float rounded to two decimals points.
     */
    protected static float roundCents(float numberToBeRounded) {
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
