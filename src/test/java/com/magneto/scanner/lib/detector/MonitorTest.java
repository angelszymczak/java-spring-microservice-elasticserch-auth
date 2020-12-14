package com.magneto.scanner.lib.detector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonitorTest {
    private final String[] factors = { "A", "C", "G", "T" };
    private final int humanFactorLimit = 1;
    private final int factorSize = 4;

    @Test
    public void whenCreateMonitor_ThenGetFactorCountReturnZero() {
        Monitor monitor = new Monitor(factorSize, humanFactorLimit, factors);

        for (String factor: factors) assertEquals(0, monitor.getFactorCount(factor));
    }

    @Test
    public void whenHashNotEnoughFactor_ThenReturnTrue() {
        Monitor monitor = new Monitor(factorSize, humanFactorLimit, factors);

        assertFalse(monitor.hashEnoughFactor());
    }

    @Test
    public void whenRecordFactor_ThenGetFactorCountReturnIncremented() {
        Monitor monitor = new Monitor(factorSize, humanFactorLimit, factors);

        String factor = "A";
        int previousFactorCount = monitor.getFactorCount(factor);
        monitor.recordFactor(factor);

        assertEquals(monitor.getFactorCount(factor), previousFactorCount + 1);
    }

    @Test
    public void whenHashEnoughFactor_ThenReturnTrue() {
        Monitor monitor = new Monitor(factorSize, humanFactorLimit, factors);

        for (int i = 0; i <= humanFactorLimit; i++) monitor.recordFactor("A");

        assertTrue(monitor.hashEnoughFactor());
    }
}
