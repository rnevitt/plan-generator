package com.robertnevitt.plangenerator.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PlanRequestDTO {
    
    @NotNull(message = "Please include duration as a whole number.")
    @Min(message = "Duration must be at least 1 month.",value = 1)
    private int duration;
    
    @NotNull(message = "Please include the nominal interest rate.")
    @Pattern(message = "Please enter an interest rate (for example 4% can be entered as 4 or 4.0 do not include %).",regexp =  "^\\d*\\.\\d+|\\d+\\.\\d*$")
    private String nominalRate;
    
    @NotNull(message = "Please include the loan amount.")
    @Pattern(message = "Please enter a loan amount, (for example $5000 can be entered as 5000 or 5000.00, w/o denomination symbol)",regexp =  "^\\d*\\.\\d+|\\d+\\.\\d*$")
    private String loanAmount;
    
    @NotNull(message = "Please include a start date in format: yyyy-mm-ddThh:mm:ssZ")
    @Pattern(message = "Date must be in format: yyyy-mm-ddThh:mm:ssZ", regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}?(?:Z|[+-][01]\\d:[0-5]\\d)$")
    private String startDate;
    
    public int getDuration() {
        return duration;
    }
    
    public String getNominalRate() {
        return nominalRate;
    }
    
    public String getLoanAmount() {
        return loanAmount;
    }
    
    public String getStartDate() {
        return startDate;
    }
}
