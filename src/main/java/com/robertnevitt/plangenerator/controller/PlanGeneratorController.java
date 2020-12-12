package com.robertnevitt.plangenerator.controller;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.robertnevitt.plangenerator.calculation.Period;
import com.robertnevitt.plangenerator.calculation.AmortizedLoan;
import com.robertnevitt.plangenerator.calculation.AmortizedLoanPayment;
import com.robertnevitt.plangenerator.dto.BorrowerPayment;
import com.robertnevitt.plangenerator.dto.PlanRequestDTO;
import com.robertnevitt.plangenerator.dto.PlanResponseDTO;

@RestController
public class PlanGeneratorController {
    
    private static final Logger logger = LogManager.getLogger(PlanGeneratorController.class);
    
    /**
     * Returns an amortized monthly loan payment plan for a 360 days year based on a 30 day month compounding.
     * <p>
     * For the purpose of this coding challenge there were no additional inputs would could change loan type,
     * compounding period, or interest rate period. 
     * 
     * 
     * @param planRequest PlanRequestDTO validated request body. 
     * @return PlanResponseDTO with an array of BorrowerPayment objects. 
     */
    @PostMapping("/generate-plan")
    PlanResponseDTO newPaymentPlan(@Valid @RequestBody PlanRequestDTO planRequest) {
        logger.debug("Debug level logger is active");
        float loanAmount = Float.valueOf(planRequest.getLoanAmount());
        float interestRate = Float.valueOf(planRequest.getNominalRate());
        ZonedDateTime startDate = ZonedDateTime.parse(planRequest.getStartDate());
        AmortizedLoan loan = AmortizedLoan.getInstance(loanAmount, interestRate, Period.YEARLY, Period.MONTHLY, planRequest.getDuration(), startDate);
        PlanResponseDTO response = new PlanResponseDTO(convertAmortizedPaymentsToBorrowerPayments(loan.generateAllBorrowerPayments()));
  
        return response;
    }
 
    /**
     * Converts an ArrayList of AmortizedLoanPayment objects into an array of BorrowerPayments.
     * <p>
     * The purpose of this conversion is that the expected JSON response to a call to 
     * /plan-generator uses Strings for all fields.
     * <p>
     * @param amortizedLoanPayments ArrayList<AmortizedLoanPayment>
     * @return BorrowerPayment[] an array of BorrowerPayment objects.
     */
    protected BorrowerPayment[] convertAmortizedPaymentsToBorrowerPayments(ArrayList<AmortizedLoanPayment> amortizedLoanPayments) {
        ArrayList<BorrowerPayment> borrowerPayments = new ArrayList<BorrowerPayment>();
        for(AmortizedLoanPayment payment : amortizedLoanPayments) {

            String borrowerPaymentAmount = String.format("%.2f",payment.borrowerPaymentAmount);
            String date = payment.datePaymentToBeMade.toString();
            String initialOutstandingPrincipal = String.format("%.2f",payment.initialOutstandingPrincipal);
            String interest = String.format("%.2f",payment.interestToBePaid);
            String principal = String.format("%.2f",payment.principalToBePaid);
            String remainingOutstandingPrincipal = String.format("%.2f",payment.remainingOutstandingPrincipal);
            
            BorrowerPayment.Builder borrowerPaymentBuilder = new BorrowerPayment.Builder(borrowerPaymentAmount, date, initialOutstandingPrincipal, interest,
                    principal, remainingOutstandingPrincipal);
            borrowerPayments.add(borrowerPaymentBuilder.build());
        }
        
        return borrowerPayments.toArray(new BorrowerPayment[borrowerPayments.size()]);
    }
   
}
