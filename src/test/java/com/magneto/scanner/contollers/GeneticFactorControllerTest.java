package com.magneto.scanner.contollers;

import com.magneto.scanner.controllers.GeneticFactorController;
import com.magneto.scanner.models.GeneticFactor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GeneticFactorControllerTest {
    @Autowired
    GeneticFactorController controller = new GeneticFactorController();

    @Test
    void whenPostDnaIsNotMutant_ThenReturn403() {
        String[] code = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        GeneticFactor sample = new GeneticFactor(code);

        ResponseEntity response = controller.create(sample);

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void whenPostDnaIsMutant_ThenReturn200() {
        String[] code = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        GeneticFactor sample = new GeneticFactor(code);

        ResponseEntity response = controller.create(sample);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
