package com.robertnevitt.plangenerator.calculation;

/**
 * This class works in our universe where every month has 30 days, and years have 360 days.
 * This ENUM not work in this form in a universe where months have more or less than 30 days
 * and years have more or less than 360 days.
 *
 * @author Robert Nevitt
 *
 */
public enum Period {
    DAILY(1), WEEKLY(7), MONTHLY(30), YEARLY(360);
    
    public final int numberOfDays;
    
    private Period(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
}
