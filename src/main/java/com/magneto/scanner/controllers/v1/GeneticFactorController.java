package com.magneto.scanner.controllers.v1;

import com.magneto.scanner.models.GeneticFactor;
import com.magneto.scanner.payload.request.ScanRequest;
import com.magneto.scanner.services.GeneticFactorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
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
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity mutant(@RequestBody ScanRequest param) {
        LOGGER.info("Request scan with " + param);

        if (!param.isValid()) {
            LOGGER.info("Invalid param");

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        GeneticFactor geneticFactor = service.findOrCreate(param.getDna());

        LOGGER.info(String.format("Result: %s", geneticFactor));

        if (geneticFactor.getMutant()) return ResponseEntity.status(HttpStatus.OK).build();
        else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}