package com.magneto.scanner.models;

import com.magneto.scanner.services.GeneticFactorService;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GeneticFactorDocumentTest {

    final String[] mutantDna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

    final String id = UUID.randomUUID().toString();
    final String dna = String.join(GeneticFactorService.DELIMITER, mutantDna);
    final Boolean isMutant = true;
    final Long timestamp = Instant.now().toEpochMilli();

    final GeneticFactorDocument document = new GeneticFactorDocument(id, dna, isMutant, timestamp);

    @Test
    void whenBuilds() {
        assertAll("document",
                () -> assertEquals(id, document.getId()),
                () -> assertEquals(dna, document.getDna()),
                () -> assertEquals(isMutant, document.getMutant()),
                () -> assertEquals(timestamp, document.getTimestamp())
        );
    }

    @Test
    void whenToString() {
        GeneticFactorDocument document = new GeneticFactorDocument(id, dna, isMutant, timestamp);

        assertTrue(document.toString().contains(String.format("[id=%s,dna=%s,mutant=%s,timestamp=%s]", id, dna, isMutant, timestamp)));
    }
}