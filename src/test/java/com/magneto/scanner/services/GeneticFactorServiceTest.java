package com.magneto.scanner.services;

import com.magneto.scanner.models.GeneticFactor;
import com.magneto.scanner.repository.GeneticFactorRepository;
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
    StatService mockedStatService = mock(StatService.class);

    @Autowired
    GeneticFactorService geneticFactorService = new GeneticFactorService(mockedRepository, mockedStatService);

    final String[] presentMutantDna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
    final String presentDnaRepository = String.join(GeneticFactorService.DELIMITER, presentMutantDna);
    final GeneticFactor presentGeneticFactor = GeneticFactor.builder()
            .dna(presentDnaRepository)
            .mutant(true)
            .build();

    final String[] notPresentHumanDna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    final String newDnaRepository = String.join(GeneticFactorService.DELIMITER, notPresentHumanDna);
    final GeneticFactor newGeneticFactor = GeneticFactor.builder()
            .dna(newDnaRepository)
            .mutant(false)
            .build();

    @Test
    void whenFindOrCreateWithPresentDna_ThenReturnPresentGeneticFactor() {
        when(mockedRepository.findByDna(presentDnaRepository))
                .thenReturn(Arrays.asList(new GeneticFactor[]{presentGeneticFactor}));

        GeneticFactor response = geneticFactorService.findOrCreate(presentMutantDna);

        assertThat(presentDnaRepository).isEqualTo(response.getDna());
    }

    @Test
    void whenFindOrCreateWithNotPresentDna_ThenReturnNewGeneticFactor() {
        when(mockedRepository.findByDna(newDnaRepository))
                .thenReturn(Collections.emptyList());

        when(mockedRepository.save(any()))
                .thenReturn(newGeneticFactor);

        GeneticFactor response = geneticFactorService.findOrCreate(notPresentHumanDna);

        assertThat(newDnaRepository).isEqualTo(response.getDna());
    }
}