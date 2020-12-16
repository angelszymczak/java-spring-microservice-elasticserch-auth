package com.magneto.scanner.services;

import com.magneto.scanner.models.GeneticFactorDocument;
import com.magneto.scanner.models.Stat;
import com.magneto.scanner.repository.GeneticFactorRepository;
import com.magneto.scanner.repository.StatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class StatServiceTest {
    GeneticFactorRepository mockedGeneticFactorRepository = mock(GeneticFactorRepository.class);
    StatRepository mockedStatRepository = mock(StatRepository.class);

    @Autowired
    StatService service = new StatService(mockedGeneticFactorRepository, mockedStatRepository);

    final GeneticFactorDocument mutantDocument = GeneticFactorDocument.builder()
            .mutant(true)
            .build();

    final GeneticFactorDocument humanDocument = GeneticFactorDocument.builder()
            .mutant(false)
            .build();


    @Test
    void getStatIsPresent_ThenReturnOldStat() {
        int mutantCount = 40;
        int humanCount = 100;
        double ratio = 0.4;

        when(mockedStatRepository.getStat()).thenReturn(new Stat(mutantCount, humanCount, ratio));

        Stat response = service.getStat();

        assertAll("stat",
                () -> assertEquals(mutantCount, response.getCount_mutant_dna()),
                () -> assertEquals(humanCount, response.getCount_human_dna()),
                () -> assertEquals(ratio, response.getRatio())
        );
    }

    @Test
    void getStatNotPresent_ThenReturnNewStat() {
        when(mockedStatRepository.getStat()).thenReturn(null);

        when(mockedGeneticFactorRepository.findByMutant(true))
                .thenReturn(Arrays.asList(new GeneticFactorDocument[]{mutantDocument, mutantDocument}));
        when(mockedGeneticFactorRepository.findByMutant(false))
                .thenReturn(Arrays.asList(new GeneticFactorDocument[]{humanDocument, humanDocument, humanDocument, humanDocument, humanDocument}));

        Stat response = service.getStat();

        assertAll("stat",
                () -> assertEquals(2, response.getCount_mutant_dna()),
                () -> assertEquals(5, response.getCount_human_dna()),
                () -> assertEquals(0.4, response.getRatio())
        );
    }

    @Test
    void recordMutantWithNonCountZero_ThenRatioIncremented() {
        List<GeneticFactorDocument> mutants = new ArrayList<>();
        int mutantCount = 40;
        for (int i = 0; i < mutantCount; i++) mutants.add(mutantDocument);
        when(mockedGeneticFactorRepository.findByMutant(true)).thenReturn(mutants);

        List<GeneticFactorDocument> humans = new ArrayList<>();
        int humanCount = 100;
        for (int i = 0; i < humanCount; i++) humans.add(humanDocument);
        when(mockedGeneticFactorRepository.findByMutant(false)).thenReturn(humans);

        double ratio = 0.4;

        Stat mutantResponse = service.record(true);
        assertAll("Stats",
                () -> assertEquals(mutantCount + 1, mutantResponse.getCount_mutant_dna()),
                () -> assertEquals(humanCount, mutantResponse.getCount_human_dna()),
                () -> assertTrue(ratio < mutantResponse.getRatio())
        );
    }
}