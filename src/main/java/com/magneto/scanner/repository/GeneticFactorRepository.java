package com.magneto.scanner.repository;

import com.magneto.scanner.models.GeneticFactorDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface GeneticFactorRepository extends ElasticsearchRepository<GeneticFactorDocument, String> {
    List<GeneticFactorDocument> findByDna(String[] dna);
}
