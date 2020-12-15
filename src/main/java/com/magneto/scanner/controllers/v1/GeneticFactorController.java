package com.magneto.scanner.controllers;

import com.magneto.scanner.models.GeneticFactorDocument;
import com.magneto.scanner.requests.GeneticFactorParam;
import com.magneto.scanner.services.GeneticFactorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneticFactorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneticFactorController.class);

    @Autowired
    public GeneticFactorService service;

    public GeneticFactorController(GeneticFactorService service) {
        this.service = service;
    }

    @ResponseBody
    @PostMapping(value = "/mutant", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody GeneticFactorParam param) {
        GeneticFactorDocument document = service.findOrCreate(param.getDna());

        if (document.getMutant()) return new ResponseEntity(HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}