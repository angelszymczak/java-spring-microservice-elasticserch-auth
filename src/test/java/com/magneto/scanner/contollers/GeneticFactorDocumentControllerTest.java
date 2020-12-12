package com.magneto.scanner.contollers;

import com.magneto.scanner.controllers.GeneticFactorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GeneticFactorDocumentControllerTest {
    @Autowired
    GeneticFactorController controller = new GeneticFactorController();

    @Test
    void whenPostDnaIsNotMutant_ThenReturn403() {
//        String[] code = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
//        GeneticFactorDocument sample = new GeneticFactorDocument(code);
//
//        ResponseEntity response = controller.create(sample);
//
//        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void whenPostDnaIsMutant_ThenReturn200() {
//        String[] code = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
//        GeneticFactorDocument sample = new GeneticFactorDocument(code);
//
//        ResponseEntity response = controller.create(sample);
//
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
