package com.robertnevitt.plangenerator.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.robertnevitt.plangenerator.dto.PlanRequestDTO;
import com.robertnevitt.plangenerator.dto.PlanResponseDTO;

@RestController
public class PlanGeneratorController {

    @PostMapping("/plans")
    PlanResponseDTO newPaymentPlan(@RequestBody PlanRequestDTO planRequest) {
        
        return new PlanResponseDTO();
    }
}
