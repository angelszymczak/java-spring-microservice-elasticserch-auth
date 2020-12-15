package com.magneto.scanner.services;

import com.magneto.scanner.lib.detector.MutantFactorDetector;
import com.magneto.scanner.models.GeneticFactorDocument;
import com.magneto.scanner.models.GeneticFactorStat;
import com.magneto.scanner.repository.GeneticFactorRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GeneticFactorService {
    @Resource
    final private GeneticFactorRepository repository;

    public static final String DELIMITER = ",";

    public GeneticFactorService(GeneticFactorRepository repository) {
        this.repository = repository;
    }

    public GeneticFactorDocument findOrCreate(String[] dna) {
        Optional<GeneticFactorDocument> document = findByDna(dna);

        return (document.isPresent()) ? document.get() : create(dna);
    }

    public GeneticFactorStat getStat() {
        int humansCount = repository.findByMutant(false).size();
        int mutantsCount = repository.findByMutant(true).size();
        double ratio = (double) mutantsCount / (double) humansCount;

        return new GeneticFactorStat(mutantsCount, humansCount, ratio);
    }

    private Optional<GeneticFactorDocument> findByDna(String[] dna) {
        String joinedDna = String.join(DELIMITER, dna);
        List<GeneticFactorDocument> documents = repository.findByDna(joinedDna);

        Boolean anyDocument = documents.stream().anyMatch(doc -> doc.getDna().equals(joinedDna));
        return (anyDocument) ? documents.stream().findFirst() : Optional.ofNullable(null);
    }

    private GeneticFactorDocument create(String[] dna) {
        return repository.save(
                GeneticFactorDocument.builder()
                        .id(UUID.randomUUID().toString())
                        .dna(String.join(DELIMITER, dna))
                        .mutant(MutantFactorDetector.isMutant(dna))
                        .timestamp(Instant.now().toEpochMilli())
                        .build()
        );
    }
}
