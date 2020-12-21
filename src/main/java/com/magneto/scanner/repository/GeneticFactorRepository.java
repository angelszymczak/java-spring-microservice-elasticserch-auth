package com.magneto.scanner.repository;

import com.magneto.scanner.models.GeneticFactor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 *
 * TODO
 *  Research about query for string arrays field types based on accuracy,
 *  currently we added a workaround to trait a DNA chain like a single String.
 *  So, it like an ID, should be unique to detect which patterns is present
 *  over specific combination, but if we would scanner a new pattern probably
 *  you'll parse it splitting by quotes "," since It to get dimensions of genetic code.
 */
@Repository
public interface GeneticFactorRepository extends ElasticsearchRepository<GeneticFactor, String> {
    List<GeneticFactor> findByDna(String dna);
    List<GeneticFactor> findByMutant(Boolean isMutant);
}
