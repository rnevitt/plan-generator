package com.robertnevitt.plangenerator.calculation;

/**
 * This class contains the structure that (I believe) every loan has:
 * An original loan amount (originalPrincipal).
 * An interest rate (nominalInterestRate) that payments are calculated against (adjustable rate mortgage
 * plans would need to be recalculated from the originalPrincipal at time of calculation.)
 * The period that the interest rate is quoted for, for example 4% annual interest would be YEARLY.
 * The compoundPeriod handles how often the interest is calculated, for example many loans calculate interest MONTHLY
 * at 1/12 of an interest rate with an annual period (interestRatePeriod).
 * <p>
 * @author Robert Nevitt
 *
 */
public abstract class Loan {
    protected final float originalPrincipal;
    protected final float nominalInterestRate;
    protected final Period interestRatePeriod;
    protected final Period compoundingPeriod;

    protected Loan(float principal, float nominalInterestRate, Period interestRatePeriod, Period compoundingPeriod) {
        this.originalPrincipal = principal;
        this.nominalInterestRate = nominalInterestRate;
        this.interestRatePeriod = interestRatePeriod;
        this.compoundingPeriod = compoundingPeriod;
    }
    
    protected float calculateRatePerPeriod() {
        return ((this.nominalInterestRate/100f)/(this.interestRatePeriod.numberOfDays/this.compoundingPeriod.numberOfDays));
    }
    
    public static float calculatInterestPayment(Loan loan, float currentPrincipal) {
        return (((loan.nominalInterestRate/100) * loan.compoundingPeriod.numberOfDays * currentPrincipal)
                / loan.interestRatePeriod.numberOfDays);
    }

}
