package com.magneto.scanner.controllers;

import com.magneto.scanner.lib.detector.MutantFactorDetector;
import com.magneto.scanner.models.GeneticFactor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class GeneticFactorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneticFactorController.class);

    @ResponseBody
    @PostMapping("/mutant")
    public ResponseEntity create(@RequestBody GeneticFactor factor) {
        MutantFactorDetector detector = new MutantFactorDetector();
        Boolean isMutant = detector.isMutant(factor.getDna());

        LOGGER.info(String.format("Factor: %s, isMutant? %s", Arrays.toString(factor.getDna()), isMutant.toString()));

        if (isMutant) return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}