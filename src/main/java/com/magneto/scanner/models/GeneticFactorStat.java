package com.magneto.scanner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GeneticFactorStat {
    private int count_mutant_dna;
    private int count_human_dna;
    private double ratio;
}
