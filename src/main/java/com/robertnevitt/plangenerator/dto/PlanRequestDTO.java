package com.robertnevitt.plangenerator.dto;

public class PlanRequestDTO {
    private int duration;
    private String nominalRate;
    private String loanAmount;
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
