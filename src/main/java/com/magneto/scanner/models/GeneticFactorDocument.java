package com.magneto.scanner.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/*
 * Genetic Factor records that can be Mutant and No-Mutant.
 * - The property `code` is an String array that represent each table row of (NxN) with the DNA sequence.
 * - The letters of the Strings can only be: (A, T, C, G), the which represents each nitrogenous base of DNA.
 * - You will know if the property `mutant` is true if you find more than one sequence of four letters
 *   equal, oblique, horizontally, or vertically.
 */
@AllArgsConstructor
@Builder
@Getter
@Document(indexName = "genetic-factor")
public class GeneticFactorDocument {
    @Id
    private String id;
    private String dna;
    private Boolean mutant;
    private Long timestamp;

    @Override
    public String toString() {
        return String.format(
                "GeneticFactorDocument: { id: %s, dna: %s, mutant: %s, timestamp: %s }",
                id, dna, mutant, timestamp
        );
    }
}

