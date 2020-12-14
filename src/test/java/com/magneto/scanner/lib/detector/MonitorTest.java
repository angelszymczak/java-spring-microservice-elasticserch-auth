package com.magneto.scanner.lib.detector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonitorTest {
    private final String[] factors = { "A", "C", "G", "T" };
    private final int humanFactorLimit = 1;
    private final int factorSize = 4;

    private Monitor monitor;

    @BeforeEach
    public void setUp() {
        monitor = new Monitor(factorSize, humanFactorLimit, factors);
    }

    @Test
    public void whenCreateMonitor_ThenGetNotHashEnoughFactorAndCountReturnZero() {
        assertAll("monitor",
                () -> assertEquals(0, monitor.accumulatedFactor()),
                () -> assertFalse(monitor.hashEnoughFactor())
        );
    }

    @Test
    public void whenRecordFactor_ThenGetFactorCountReturnIncremented() {
        String factor = "A";
        int previousFactorCount = monitor.accumulatedFactor();

        monitor.recordFactor(factor);

        assertEquals(previousFactorCount + 1, monitor.accumulatedFactor());
    }

    @Test
    public void whenHashEnoughFactor_ThenReturnTrue() {
        String factor = "A";

        for (int i = 0; i <= humanFactorLimit; i++) monitor.recordFactor(factor);

        assertTrue(monitor.hashEnoughFactor());
    }
}
