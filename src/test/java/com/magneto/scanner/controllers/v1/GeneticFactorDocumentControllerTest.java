package com.magneto.scanner.controllers.v1;

import com.magneto.scanner.models.GeneticFactorDocument;
import com.magneto.scanner.requests.GeneticFactorParam;
import com.magneto.scanner.services.GeneticFactorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeneticFactorDocumentControllerTest {
    GeneticFactorService mockedService = mock(GeneticFactorService.class);

    @Autowired
    GeneticFactorController controller = new GeneticFactorController(mockedService);

    final String[] mutantDnaParam = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
    final String[] humanDnaParam = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

    @Test
    void whenPostDnaIsNotMutant_ThenReturn403() {
        when(mockedService.findOrCreate(humanDnaParam))
                .thenReturn(
                        GeneticFactorDocument.builder()
                                .dna(String.join(GeneticFactorService.DELIMITER, humanDnaParam))
                                .mutant(false)
                                .build()
                );

        ResponseEntity response = controller.mutant(new GeneticFactorParam(humanDnaParam));

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void whenPostDnaIsMutant_ThenReturn200() {
        when(mockedService.findOrCreate(mutantDnaParam))
                .thenReturn(
                        GeneticFactorDocument.builder()
                                .dna(String.join(GeneticFactorService.DELIMITER, mutantDnaParam))
                                .mutant(true)
                                .build()
                );

        ResponseEntity response = controller.mutant(new GeneticFactorParam(mutantDnaParam));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
