package com.magneto.scanner.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Stat implements Serializable {
    private int count_mutant_dna;
    private int count_human_dna;
    private double ratio;
}
