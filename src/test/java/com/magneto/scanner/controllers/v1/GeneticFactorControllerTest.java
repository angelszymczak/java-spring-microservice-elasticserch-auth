package com.magneto.scanner.controllers.v1;

import com.magneto.scanner.models.GeneticFactor;
import com.magneto.scanner.payload.request.ScanRequest;
import com.magneto.scanner.services.GeneticFactorService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeneticFactorControllerTest {

    final String[] mutantDnaParam = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
    final String[] humanDnaParam = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

    private GeneticFactorService mockedService = mock(GeneticFactorService.class);
    private GeneticFactorController controller = new GeneticFactorController(mockedService);

    @Test
    void whenPostDnaIsNotMutant_ThenReturn403() {
        when(mockedService.findOrCreate(humanDnaParam))
                .thenReturn(
                        GeneticFactor.builder()
                                .dna(String.join(GeneticFactorService.DELIMITER, humanDnaParam))
                                .mutant(false)
                                .build()
                );

        ResponseEntity response = controller.mutant(new ScanRequest(humanDnaParam));

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void whenPostDnaIsMutant_ThenReturn200() {
        when(mockedService.findOrCreate(mutantDnaParam))
                .thenReturn(
                        GeneticFactor.builder()
                                .dna(String.join(GeneticFactorService.DELIMITER, mutantDnaParam))
                                .mutant(true)
                                .build()
                );

        ResponseEntity response = controller.mutant(new ScanRequest(mutantDnaParam));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
