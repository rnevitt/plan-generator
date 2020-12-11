package com.robertnevitt.plangenerator.controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.robertnevitt.plangenerator.calculation.Period;
import com.robertnevitt.plangenerator.calculation.SimpleLoan;
import com.robertnevitt.plangenerator.calculation.SimpleLoanPayment;
import com.robertnevitt.plangenerator.dto.BorrowerPayment;
import com.robertnevitt.plangenerator.dto.PlanRequestDTO;
import com.robertnevitt.plangenerator.dto.PlanResponseDTO;

@RestController
public class PlanGeneratorController {
    
    private static final Logger logger = LogManager.getLogger(PlanGeneratorController.class);
    
    @PostMapping("/plans")
    PlanResponseDTO newPaymentPlan(@Valid @RequestBody PlanRequestDTO planRequest) {
        logger.debug("Debug level logger is active");
        float loanAmount = Float.valueOf(planRequest.getLoanAmount());
        float interestRate = Float.valueOf(planRequest.getNominalRate());
        ZonedDateTime startDate = ZonedDateTime.parse(planRequest.getStartDate());
        logger.debug("startDate :"+startDate.getMonthValue());
        SimpleLoan loan = SimpleLoan.getInstance(loanAmount, interestRate, Period.YEARLY, Period.MONTHLY, planRequest.getDuration(), startDate);
        PlanResponseDTO response = new PlanResponseDTO(convertSimpleLoanPaymentsToBorrowerPayments(loan.generateAllBorrowerPayments()));
  
        return response;
    }
 
    private BorrowerPayment[] convertSimpleLoanPaymentsToBorrowerPayments(ArrayList<SimpleLoanPayment> simpleLoanPayments) {
        ArrayList<BorrowerPayment> borrowerPayments = new ArrayList<BorrowerPayment>();
        for(SimpleLoanPayment payment : simpleLoanPayments) {
           // String borrowerPaymentAmount = String.format("%.2f", payment.borrowerPaymentAmount);
            logger.debug("borrowerpayment before round" + payment.borrowerPaymentAmount );
            String borrowerPaymentAmount = String.format("%.2f",payment.borrowerPaymentAmount);
            String date = payment.date.toString();
            String initialOutstandingPrincipal = String.format("%.2f",payment.initialOutstandingPrincipal);
            String interest = String.format("%.2f",payment.interestPaid);
            String principal = String.format("%.2f",payment.principalPaid);
            String remainingOutstandingPrincipal = String.format("%.2f",payment.remainingOustandingPrincipal);
            BorrowerPayment.Builder borrowerPaymentBuilder = new BorrowerPayment.Builder(borrowerPaymentAmount, date, initialOutstandingPrincipal, interest,
                    principal, remainingOutstandingPrincipal);
            borrowerPayments.add(borrowerPaymentBuilder.build());
        }
        
        return borrowerPayments.toArray(new BorrowerPayment[borrowerPayments.size()]);
    }
    
    //TODO: move to SimpleLoan
    private String roundCents(float numberToBeRounded) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(numberToBeRounded);
    }
}
