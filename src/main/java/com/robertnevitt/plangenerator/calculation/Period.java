package com.robertnevitt.plangenerator.calculation;

public enum Period {
    DAILY(1), WEEKLY(7), MONTHLY(30), YEARLY(360);
    
    public final int numberOfDays;
    
    private Period(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
}
