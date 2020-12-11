package com.robertnevitt.plangenerator.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class PlanGeneratorControllerTest {
    private static final String JSON_BODY_REQUEST_CORRECT = ("{\n"
            + "    \"loanAmount\": \"5000.00\",\n"
            + "    \"nominalRate\": \"5.0\",\n"
            + "    \"duration\": 24,\n"
            + "    \"startDate\": \"2018-01-01T00:00:01Z\"\n"
            + "}");
    
    private static final String JSON_BODY_REQUEST_MALFORMED = ("{\n"
            + "    \"nominalRate\": \"6.01\",\n"
            + "    \"duration\": 2,\n"
            + "    \"startDate\": \"2018-01-01T00:00:01+05:00\"\n"
            + "}");
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void newPaymentPlanNoBodyTest() throws Exception {
        this.mockMvc.perform(post("/plans")).andExpect(status().isBadRequest());
    }

    @Test
    public void newPaymentPlanTest() throws Exception {
      
        this.mockMvc.perform(post("/plans").contentType(MediaType.APPLICATION_JSON).content(JSON_BODY_REQUEST_CORRECT))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.borrowerPayments").isArray())
            .andExpect(jsonPath("$.borrowerPayments[0].borrowerPaymentAmount").isString())
            .andExpect(jsonPath("$.borrowerPayments[0].borrowerPaymentAmount", is("219.36")))
            .andExpect(jsonPath("$.borrowerPayments[0].date").isString())
            .andExpect(jsonPath("$.borrowerPayments[0].date", is("2018-01-01T00:00:01Z")))
            .andExpect(jsonPath("$.borrowerPayments[0].initialOutstandingPrincipal").isString())
            .andExpect(jsonPath("$.borrowerPayments[0].initialOutstandingPrincipal", is("5000.00")))
            .andExpect(jsonPath("$.borrowerPayments[0].interest").isString())
            .andExpect(jsonPath("$.borrowerPayments[0].interest", is("20.83")))
            .andExpect(jsonPath("$.borrowerPayments[0].principal").isString())
            .andExpect(jsonPath("$.borrowerPayments[0].principal", is("198.53")))
            .andExpect(jsonPath("$.borrowerPayments[0].remainingOutstandingPrincipal").isString())
            .andExpect(jsonPath("$.borrowerPayments[0].remainingOutstandingPrincipal", is("4801.47")));
    }
    
    @Test
    public void newPaymentPlanFinalPaymentTest() throws Exception {
      
        this.mockMvc.perform(post("/plans").contentType(MediaType.APPLICATION_JSON).content(JSON_BODY_REQUEST_CORRECT))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.borrowerPayments").isArray())
            .andExpect(jsonPath("$.borrowerPayments[23].borrowerPaymentAmount").isString())
            .andExpect(jsonPath("$.borrowerPayments[23].borrowerPaymentAmount", is("219.28")))
            .andExpect(jsonPath("$.borrowerPayments[23].date").isString())
            .andExpect(jsonPath("$.borrowerPayments[23].date", is("2019-12-01T00:00:01Z")))
            .andExpect(jsonPath("$.borrowerPayments[23].initialOutstandingPrincipal").isString())
            .andExpect(jsonPath("$.borrowerPayments[23].initialOutstandingPrincipal", is("218.37")))
            .andExpect(jsonPath("$.borrowerPayments[23].interest").isString())
            .andExpect(jsonPath("$.borrowerPayments[23].interest", is("0.91")))
            .andExpect(jsonPath("$.borrowerPayments[23].principal").isString())
            .andExpect(jsonPath("$.borrowerPayments[23].principal", is("218.37")))
            .andExpect(jsonPath("$.borrowerPayments[23].remainingOutstandingPrincipal").isString())
            .andExpect(jsonPath("$.borrowerPayments[23].remainingOutstandingPrincipal", is("0.00")));
    }

    
    @Test
    public void newPaymentPlanMalformedRequestTest() throws Exception {
      
        this.mockMvc.perform(post("/plans").contentType(MediaType.APPLICATION_JSON).content(JSON_BODY_REQUEST_MALFORMED))
            .andExpect(status().isBadRequest());
    }
    
    
}
