package com.magneto.scanner.services;

import com.magneto.scanner.models.GeneticFactorDocument;
import com.magneto.scanner.repository.GeneticFactorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GeneticFactorServiceTest {
    GeneticFactorRepository mockedRepository = mock(GeneticFactorRepository.class);

    @Autowired
    GeneticFactorService service = new GeneticFactorService(mockedRepository);

    final String[] presentMutantDna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
    final String presentDnaRepository = String.join(GeneticFactorService.DELIMITER, presentMutantDna);
    final GeneticFactorDocument presentDocument = GeneticFactorDocument.builder()
            .dna(presentDnaRepository)
            .mutant(true)
            .build();

    final String[] notPresentHumanDna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    final String newDnaRepository = String.join(GeneticFactorService.DELIMITER, notPresentHumanDna);
    final GeneticFactorDocument newDocument = GeneticFactorDocument.builder()
            .dna(newDnaRepository)
            .mutant(false)
            .build();

    @BeforeEach
    void setUp() {
        when(mockedRepository.findByDna(presentDnaRepository))
                .thenReturn(Arrays.asList(new GeneticFactorDocument[]{presentDocument}));

        when(mockedRepository.findByDna(newDnaRepository))
                .thenReturn(Collections.emptyList());

        when(mockedRepository.save(any()))
                .thenReturn(newDocument);
    }

    @Test
    void whenFindOrCreateWithPresentDna_ThenReturnPresentDocument() {
        GeneticFactorDocument response = service.findOrCreate(presentMutantDna);

        assertThat(presentDnaRepository).isEqualTo(response.getDna());
    }

    @Test
    void whenFindOrCreateWithNotPresentDna_ThenReturnNewDocument() {
        GeneticFactorDocument response = service.findOrCreate(notPresentHumanDna);

        assertThat(newDnaRepository).isEqualTo(response.getDna());
    }
}