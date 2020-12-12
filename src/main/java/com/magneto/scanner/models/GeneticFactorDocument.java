package com.magneto.scanner.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "genetic-factor")
public class GeneticFactorDocument {
    @Id
    private String id;
    private String[] dna;
    private Long timestamp;

}

