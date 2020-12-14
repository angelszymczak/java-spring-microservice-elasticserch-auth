package com.magneto.scanner.lib.detector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MutantFactorDetectorTest {

    @Test
    void whenMutantFactoDetectorBuilds() {
        Monitor monitor = Monitor.buildMutantMonitor();

        new MutantFactorDetector(monitor);
    }

    @Test
    void whenDnaIsNotMutant() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

        assertFalse(MutantFactorDetector.isMutant(dna));
    }

    @Test
    void whenDnaIsMutant() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        assertTrue(MutantFactorDetector.isMutant(dna));
    }
}
