package com.robertnevitt.plangenerator.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

    @PostMapping("/plans")
    PlanResponseDTO newPaymentPlan(@RequestBody PlanRequestDTO planRequest) {
        SimpleLoan loan = SimpleLoan.getInstance(5000f, 5.0f, Period.YEARLY, Period.MONTHLY, 24, LocalDateTime.now());
        ArrayList<SimpleLoanPayment> payments = loan.generateAllBorrowerPayments();
        return new PlanResponseDTO();
    }
}
