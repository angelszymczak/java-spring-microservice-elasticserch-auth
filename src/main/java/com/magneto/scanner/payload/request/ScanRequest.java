package com.magneto.scanner.payload.request;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ScanRequest {
    private String[] dna;
}
