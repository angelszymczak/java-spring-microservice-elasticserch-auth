package com.magneto.scanner.models;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Stat implements Serializable {
    private int count_mutant_dna;
    private int count_human_dna;
    private double ratio;
}
