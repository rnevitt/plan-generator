package com.robertnevitt.plangenerator.dto;

import java.util.ArrayList;

public class PlanResponseDTO {
	private ArrayList<BorrowerPayment> borrowerPayments;
	
	public ArrayList<BorrowerPayment> getBorrowerPayments() {
        return borrowerPayments;
    }

}
