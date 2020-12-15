package com.magneto.scanner.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GeneticFactorStatResponse {
    private int count_mutant_dna;
    private int count_human_dna;
    private double ratio;
}
