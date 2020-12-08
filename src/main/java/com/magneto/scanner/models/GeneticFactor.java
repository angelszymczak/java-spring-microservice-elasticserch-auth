package com.magneto.scanner.models;

public class GeneticFactor {
    private String[] dna;

    public GeneticFactor() {
    }

    public GeneticFactor(String[] dna) {
        this.dna = dna;
    }

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }
}

