package com.magneto.scanner.controllers.v1;

import com.magneto.scanner.models.Stat;
import com.magneto.scanner.services.StatService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StatsControllerTest {

    StatService mockedService = mock(StatService.class);
    StatsController controller = new StatsController(mockedService);

    @Test
    void whenStats_ThenReturnGeneticCodeStat200() throws IOException {
        int mutantsCount = 40;
        int humansCount = 100;
        double ratio = 0.4;

        Stat stat = new Stat(mutantsCount, humansCount, ratio);
        when(mockedService.getStat()).thenReturn(stat);

        ResponseEntity<Stat> response = controller.stats();

        assertAll("Genetic Code status",
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(mutantsCount, response.getBody().getCount_mutant_dna()),
                () -> assertEquals(humansCount, response.getBody().getCount_human_dna()),
                () -> assertEquals(ratio, response.getBody().getRatio())
        );
    }
}