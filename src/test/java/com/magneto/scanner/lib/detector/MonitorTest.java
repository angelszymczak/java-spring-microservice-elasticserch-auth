package com.magneto.scanner.lib.detector;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MonitorTest {
    private final String[] factors = { "A", "C", "G", "T" };
    private final int humanFactorLimit = 1;
    private final int factorSize = 4;

    @Test
    public void whenCreateMonitor_ThenGetFactorCountReturnZero() {
        Monitor monitor = new Monitor(factorSize, humanFactorLimit, factors);

        for (String factor: factors) Assertions.assertThat(monitor.getFactorCount(factor)).isZero();
    }

    @Test
    public void whenHashNotEnoughFactor_ThenReturnTrue() {
        Monitor monitor = new Monitor(factorSize, humanFactorLimit, factors);

        Assertions.assertThat(monitor.hashEnoughFactor()).isFalse();
    }

    @Test
    public void whenRecordFactor_ThenGetFactorCountReturnIncremented() {
        Monitor monitor = new Monitor(factorSize, humanFactorLimit, factors);

        String factor = "A";
        int previousFactorCount = monitor.getFactorCount(factor);
        monitor.recordFactor(factor);

        Assertions.assertThat(monitor.getFactorCount(factor)).isEqualTo(previousFactorCount + 1);
    }

    @Test
    public void whenHashEnoughFactor_ThenReturnTrue() {
        Monitor monitor = new Monitor(factorSize, humanFactorLimit, factors);

        for (int i = 0; i <= humanFactorLimit; i++) monitor.recordFactor("A");

        Assertions.assertThat(monitor.hashEnoughFactor()).isTrue();
    }
}
