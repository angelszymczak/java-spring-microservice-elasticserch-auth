package com.magneto.scanner.lib.detector;

@FunctionalInterface
public interface IFactorIndex {
    int next(int currentIndex);
}