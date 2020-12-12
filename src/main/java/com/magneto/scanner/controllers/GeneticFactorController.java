package com.magneto.scanner.controllers;

import com.magneto.scanner.lib.detector.MutantFactorDetector;
import com.magneto.scanner.models.GeneticFactorDocument;
import com.magneto.scanner.repository.GeneticFactorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
public class GeneticFactorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneticFactorController.class);

    @Resource
    private GeneticFactorRepository repository;

    @ResponseBody
    @PostMapping("/mutant")
    public ResponseEntity create(@RequestBody GeneticFactorDocument factor) {
        MutantFactorDetector detector = new MutantFactorDetector();
        Boolean isMutant = detector.isMutant(factor.getDna());

        LOGGER.info(String.format("Factor: %s, isMutant? %s", Arrays.toString(factor.getDna()), isMutant.toString()));

        if (isMutant) return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @PostMapping(value = "/create")
    public void create2(@RequestBody GeneticFactorDocument factor) {
        System.out.println("hola");

//        GeneticFactorDocument document = GeneticFactorDocument.builder()
//                .id(UUID.randomUUID().toString())
//                .dna(styleNo)
//                .info("style is created with no " + styleNo)
//                .validTo(new Date())
//                .build();

        repository.save(factor);
    }

    @PostMapping(value = "/find")
    public List<GeneticFactorDocument> find(@RequestBody GeneticFactorDocument factor) {
        List<GeneticFactorDocument> res = repository.findByDna(factor.getDna());
        return res;
    }
}