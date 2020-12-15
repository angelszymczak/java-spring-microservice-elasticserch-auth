package com.magneto.scanner.services;

import com.magneto.scanner.models.GeneticFactorDocument;
import com.magneto.scanner.models.GeneticFactorStat;
import com.magneto.scanner.repository.GeneticFactorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void whenGetStats_ThenReturnGeneticFactorStat() {
        int mutantsCount = 40;
        List<GeneticFactorDocument> mutants = new ArrayList<>();
        for (int i = 0; i < mutantsCount; i++) mutants.add(GeneticFactorDocument.builder().mutant(true).build());
        when(mockedRepository.findByMutant(true)).thenReturn(mutants);

        int humansCount = 100;
        List<GeneticFactorDocument> humans = new ArrayList<>();
        for (int i = 0; i < humansCount; i++) humans.add(GeneticFactorDocument.builder().mutant(false).build());
        when(mockedRepository.findByMutant(false)).thenReturn(humans);

        double ration = 0.4;

        GeneticFactorStat stat = service.getStat();

        assertAll("Genetic Factor Stat",
                () -> assertEquals(mutantsCount, stat.getCount_mutant_dna()),
                () -> assertEquals(humansCount, stat.getCount_human_dna()),
                () -> assertEquals(ration, stat.getRatio())
        );
    }
}