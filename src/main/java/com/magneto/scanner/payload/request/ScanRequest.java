package com.magneto.scanner.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ScanRequest {
    private String[] dna;

    public boolean isValid() {
        return Arrays
                .stream(dna)
                .allMatch(factor -> factor.length() == dna.length);
    }
}
