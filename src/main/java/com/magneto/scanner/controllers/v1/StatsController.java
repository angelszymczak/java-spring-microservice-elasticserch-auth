package com.magneto.scanner.controllers.v1;


import com.magneto.scanner.models.Stat;
import com.magneto.scanner.services.StatService;
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
public class StatsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneticFactorController.class);

    @Autowired
    private StatService statService;

    public StatsController(StatService statService) {
        this.statService = statService;
    }

    @ResponseBody
    @GetMapping(value = "/stats", produces = "application/vnd.magneto.v1+json")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Stat> stats() {
        Stat stat = statService.getStat();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stat);
    }
}
