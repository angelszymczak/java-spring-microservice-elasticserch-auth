package com.magneto.scanner.controllers.v1;

import com.magneto.scanner.models.GeneticFactorDocument;
import com.magneto.scanner.models.GeneticFactorStat;
import com.magneto.scanner.requests.GeneticFactorParam;
import com.magneto.scanner.services.GeneticFactorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/v1", headers = "X-Accept-Version=v1")
public class GeneticFactorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneticFactorController.class);

    @Autowired
    public GeneticFactorService service;

    public GeneticFactorController(GeneticFactorService service) {
        this.service = service;
    }

    @ResponseBody
    @PostMapping(value = "/mutant", consumes = "application/vnd.magneto.v1+json")
    public ResponseEntity mutant(@RequestBody GeneticFactorParam param) {
        GeneticFactorDocument document = service.findOrCreate(param.getDna());

        if (document.getMutant()) return ResponseEntity.status(HttpStatus.OK).build();
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ResponseBody
    @PostMapping(value = "/stats", produces = "application/vnd.magneto.v1+json")
    public ResponseEntity<GeneticFactorStat> stats() {
        GeneticFactorStat factorStat = service.getStat();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(factorStat);
    }
}