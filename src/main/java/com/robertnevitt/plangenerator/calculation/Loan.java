package com.robertnevitt.plangenerator.calculation;

public abstract class Loan {
    protected float principal;
    protected final float nominalInterestRate;
    protected final Period interestRatePeriod;
    protected final Period compoundingPeriod;

    protected Loan(float principle, float nominalInterestRate, Period interestRatePeriod, Period compoundingPeriod) {
        this.principal = principle;
        this.nominalInterestRate = nominalInterestRate;
        this.interestRatePeriod = interestRatePeriod;
        this.compoundingPeriod = compoundingPeriod;
    }
    
    public static float calculatInterestPayment(Loan loan, float currentPrincipal) {
        return (((loan.nominalInterestRate/100) * loan.compoundingPeriod.numberOfDays * currentPrincipal)
                / loan.interestRatePeriod.numberOfDays);
    }

 
}
