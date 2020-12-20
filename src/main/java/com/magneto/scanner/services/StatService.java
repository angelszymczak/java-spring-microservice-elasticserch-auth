package com.magneto.scanner.services;

import com.magneto.scanner.models.Stat;
import com.magneto.scanner.repository.GeneticFactorRepository;
import com.magneto.scanner.repository.StatRepository;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;


@Service
public class StatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatService.class);

    @Resource
    final public GeneticFactorRepository geneticFactorRepository;

    @Resource
    final public StatRepository statRepository;

    public StatService(GeneticFactorRepository geneticFactorRepository, StatRepository statRepository) {
        this.geneticFactorRepository = geneticFactorRepository;
        this.statRepository = statRepository;
    }

    public Stat getStat() {
        Optional<Stat> optionalStat = Optional.ofNullable(statRepository.getStat());

        Stat stat;
        if (optionalStat.isPresent()) {
            LOGGER.info("Retrieving stats");

            stat = optionalStat.get();
        } else {
            LOGGER.info("Initializing stats");

            int mutantCount = geneticFactorRepository.findByMutant(true).size();
            LOGGER.info(String.format("Retrieved mutant counts: %s", mutantCount));

            int humanCount = geneticFactorRepository.findByMutant(false).size();
            LOGGER.info(String.format("Retrieved human counts: %s", humanCount));

            double ratio;
            if (mutantCount == 0 || humanCount == 0) ratio = 0;
            else ratio = Precision.round((double) mutantCount / (double) humanCount, 2);

            stat = Stat.builder()
                    .count_mutant_dna(mutantCount)
                    .count_human_dna(humanCount)
                    .ratio(ratio)
                    .build();

            LOGGER.info(String.format("Storing new stat: %s", stat));
            statRepository.save(stat);
        }

        return stat;
    }

    public Stat record(Boolean isMutant) {
        Stat stat = getStat();
        int mutantCount = stat.getCount_mutant_dna();
        int humanCount = stat.getCount_human_dna();

        if (isMutant) {
            mutantCount++;
            stat.setCount_mutant_dna(mutantCount);
        } else {
            humanCount++;
            stat.setCount_human_dna(humanCount);
        }

        if (mutantCount == 0 || humanCount == 0) stat.setRatio(0);
        else stat.setRatio(Precision.round((double) mutantCount / (double) humanCount, 2));

        statRepository.save(stat);

        return stat;
    }
}
