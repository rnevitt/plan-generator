package com.robertnevitt.plangenerator.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.robertnevitt.plangenerator.calculation.Period;
import com.robertnevitt.plangenerator.calculation.SimpleLoan;
import com.robertnevitt.plangenerator.calculation.SimpleLoanPayment;
import com.robertnevitt.plangenerator.dto.PlanRequestDTO;
import com.robertnevitt.plangenerator.dto.PlanResponseDTO;

@RestController
public class PlanGeneratorController {
    
    private static final Logger logger = LogManager.getLogger(PlanGeneratorController.class);
    
    @PostMapping("/plans")
    PlanResponseDTO newPaymentPlan(@Valid @RequestBody PlanRequestDTO planRequest) {
        logger.debug("Debug level logger is active");
        SimpleLoan loan = SimpleLoan.getInstance(5000f, 5.0f, Period.YEARLY, Period.MONTHLY, 24, LocalDateTime.now());
        ArrayList<SimpleLoanPayment> payments = loan.generateAllBorrowerPayments();
        return new PlanResponseDTO();
    }
}
