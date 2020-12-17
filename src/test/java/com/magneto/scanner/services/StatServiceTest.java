package com.magneto.scanner.services;

import com.magneto.scanner.models.GeneticFactorDocument;
import com.magneto.scanner.models.Stat;
import com.magneto.scanner.repository.GeneticFactorRepository;
import com.magneto.scanner.repository.StatRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class StatServiceTest {
    GeneticFactorRepository mockedGeneticFactorRepository = mock(GeneticFactorRepository.class);
    StatRepository mockedStatRepository = mock(StatRepository.class);

    @Autowired
    StatService service = new StatService(mockedGeneticFactorRepository, mockedStatRepository);

    int mutantCount = 2;
    int humanCount = 5;
    double ratio = 0.4;

    private void mockGeneticFactorRepository(int count, boolean mutant) {
        GeneticFactorDocument document = GeneticFactorDocument.builder().mutant(mutant).build();

        List<GeneticFactorDocument> documents = new ArrayList<>();
        for (int i = 0; i < count; i++) documents.add(document);

        when(mockedGeneticFactorRepository.findByMutant(mutant)).thenReturn(documents);
    }

    @Test
    void getStatIsPresent_ThenReturnOldStat() {
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

        mockGeneticFactorRepository(mutantCount, true);
        mockGeneticFactorRepository(humanCount, false);

        Stat response = service.getStat();

        assertAll("stat",
                () -> assertEquals(mutantCount, response.getCount_mutant_dna()),
                () -> assertEquals(humanCount, response.getCount_human_dna()),
                () -> assertEquals(ratio, response.getRatio())
        );
    }

    @Test
    void recordMutantWithNonCountZero_ThenRatioIncremented() {
        mockGeneticFactorRepository(mutantCount, true);
        mockGeneticFactorRepository(humanCount, false);

        Stat response = service.record(true);

        assertAll("Stats",
                () -> assertEquals(mutantCount + 1, response.getCount_mutant_dna()),
                () -> assertEquals(humanCount, response.getCount_human_dna()),
                () -> assertTrue(ratio < response.getRatio())
        );
    }

    @Test
    void recordMutantWithHumanCountZero_ThenRatioIsZero() {
        mockGeneticFactorRepository(mutantCount, true);

        Stat response = service.record(true);

        assertAll("Stats",
                () -> assertEquals(mutantCount + 1, response.getCount_mutant_dna()),
                () -> assertEquals(0, response.getCount_human_dna()),
                () -> assertEquals(0, response.getRatio())
        );
    }

    @Test
    void recordHumanWithNonCountZero_ThenRatioIncremented() {
        mockGeneticFactorRepository(mutantCount, true);
        mockGeneticFactorRepository(humanCount, false);

        Stat response = service.record(false);

        assertAll("Stats",
                () -> assertEquals(mutantCount, response.getCount_mutant_dna()),
                () -> assertEquals(humanCount + 1, response.getCount_human_dna()),
                () -> assertTrue(ratio > response.getRatio())
        );
    }

    @Test
    void recordHumanWithMutantCountZero_ThenRatioIsZero() {
        mockGeneticFactorRepository(humanCount, false);

        Stat response = service.record(false);

        assertAll("Stats",
                () -> assertEquals(0, response.getCount_mutant_dna()),
                () -> assertEquals(humanCount + 1, response.getCount_human_dna()),
                () -> assertEquals(0, response.getRatio())
        );
    }
}