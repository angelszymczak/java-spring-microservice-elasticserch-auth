package com.magneto.scanner.services;

import com.magneto.scanner.lib.detector.MutantFactorDetector;
import com.magneto.scanner.models.GeneticFactor;
import com.magneto.scanner.repository.GeneticFactorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GeneticFactorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneticFactorService.class);

    @Resource
    final private GeneticFactorRepository repository;

    @Autowired
    public StatService statService;

    public static final String DELIMITER = ",";

    public GeneticFactorService(GeneticFactorRepository repository, StatService statService) {
        this.repository = repository;
        this.statService = statService;
    }

    public GeneticFactor findOrCreate(String[] dna) {
        Optional<GeneticFactor> geneticFactor = findByDna(dna);

        if (geneticFactor.isPresent()) {
            LOGGER.info("Retrieving DNA record");

            return geneticFactor.get();
        } else {
            LOGGER.info("Creating new DNA record");

            return create(dna);
        }
    }

    private Optional<GeneticFactor> findByDna(String[] dna) {
        String joinedDna = String.join(DELIMITER, dna);
        List<GeneticFactor> geneticFactors = repository.findByDna(joinedDna);

        Boolean anyGeneticFactor = geneticFactors.stream().anyMatch(doc -> doc.getDna().equals(joinedDna));
        return (anyGeneticFactor) ? geneticFactors.stream().findFirst() : Optional.ofNullable(null);
    }

    private GeneticFactor create(String[] dna) {
        GeneticFactor geneticFactor = repository.save(GeneticFactor.builder()
                .id(UUID.randomUUID().toString())
                .dna(String.join(DELIMITER, dna))
                .mutant(MutantFactorDetector.isMutant(dna))
                .timestamp(Instant.now().toEpochMilli())
                .build());

        statService.record(geneticFactor.getMutant());

        return geneticFactor;
    }
}
