package com.robertnevitt.plangenerator.calculation;

public abstract class Loan {
    protected float principle;
    protected final float nominalInterestRate;
    protected final Period interestRatePeriod;
    protected final Period compoundingPeriod;

    protected Loan(float principle, float nominalInterestRate, Period interestRatePeriod, Period compoundingPeriod) {
        this.principle = principle;
        this.nominalInterestRate = nominalInterestRate;
        this.interestRatePeriod = interestRatePeriod;
        this.compoundingPeriod = compoundingPeriod;
    }

    public static float calculatInterestPayment(Loan loan) {
        return ((loan.nominalInterestRate * loan.compoundingPeriod.numberOfDays * loan.principle)
                / loan.interestRatePeriod.numberOfDays);
    }
    
    protected abstract float calculatePaymentAppliedToPrinciple();
}
