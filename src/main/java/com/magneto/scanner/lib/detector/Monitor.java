package com.magneto.scanner.lib.detector;

import java.util.HashMap;
import java.util.Map;

public class Monitor {

    private Map<String, Integer> factorFlags;
    private int humanFactorLimit;
    private int factorSize;

    public static Monitor buildMutantMonitor() {
        int factorSize = 4;
        int humanFactorLimit = 1;
        String[] factors = {"A", "C", "G", "T"};

        return new Monitor(factorSize, humanFactorLimit, factors);
    }

    public Monitor(int factorSize, int humanFactorLimit, String[] factors) {
        this.factorSize = factorSize;
        this.humanFactorLimit = humanFactorLimit;
        this.factorFlags = new HashMap();
        for (String factor: factors) this.setupFactorFlag(factor);
    }

    public int getFactorSize() {
        return factorSize;
    }

    private void setupFactorFlag(String factorFlag) {
        factorFlags.put(factorFlag, 0);
    }

    public int getFactorCount(String factorFlag) {
        return factorFlags.get(factorFlag);
    }

    public void recordFactor(String flagKey) {
        int flagCounter = factorFlags.get(flagKey) + 1;

        factorFlags.put(flagKey, flagCounter);
    }

    public boolean hashEnoughFactor() {
        int accumulatedFactor = factorFlags.values().stream().reduce(0, Integer::sum);

        return accumulatedFactor > humanFactorLimit;
    }
}
