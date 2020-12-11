package com.robertnevitt.plangenerator.dto;

public class PlanResponseDTO {
	private final BorrowerPayment[] borrowerPayments;
	
	public PlanResponseDTO(BorrowerPayment[] payments) {
	     this.borrowerPayments = payments;
    }

    public BorrowerPayment[] getBorrowerPayments() {
        return borrowerPayments;
    }

}
