package com.magneto.scanner.models;

import com.magneto.scanner.services.GeneticFactorService;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GeneticFactorTest {

    final String[] mutantDna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

    final String id = UUID.randomUUID().toString();
    final String dna = String.join(GeneticFactorService.DELIMITER, mutantDna);
    final Boolean isMutant = true;
    final Long timestamp = Instant.now().toEpochMilli();

    final GeneticFactor geneticFactor = new GeneticFactor(id, dna, isMutant, timestamp);

    @Test
    void whenBuilds() {
        assertAll("Genetic Factor",
                () -> assertEquals(id, geneticFactor.getId()),
                () -> assertEquals(dna, geneticFactor.getDna()),
                () -> assertEquals(isMutant, geneticFactor.getMutant()),
                () -> assertEquals(timestamp, geneticFactor.getTimestamp())
        );
    }

    @Test
    void whenToString() {
        GeneticFactor geneticFactor = new GeneticFactor(id, dna, isMutant, timestamp);

        assertTrue(geneticFactor.toString().contains(String.format("GeneticFactor(id=%s, dna=%s, mutant=%s, timestamp=%s)", id, dna, isMutant, timestamp)));
    }
}