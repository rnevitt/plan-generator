package com.robertnevitt.plangenerator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.robertnevitt.plangenerator.controller.PlanGeneratorController;

@SpringBootTest
class PlangeneratorApplicationTests {
    @Autowired
    private PlanGeneratorController planGeneratorController;
    
	@Test
	void contextLoads() {
	    assertThat(planGeneratorController).isNotNull();
	}

}
