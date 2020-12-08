package com.magneto.scanner.lib.detector;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MutantFactorDetectorTest {
    private final MutantFactorDetector mutantFactorDetector = new MutantFactorDetector();

    @Test
    void whenDnaIsNotMutant() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

        Assertions.assertThat(mutantFactorDetector.isMutant(dna)).isFalse();
    }

    @Test
    void whenDnaIsMutant() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        Assertions.assertThat(mutantFactorDetector.isMutant(dna)).isTrue();
    }
}
