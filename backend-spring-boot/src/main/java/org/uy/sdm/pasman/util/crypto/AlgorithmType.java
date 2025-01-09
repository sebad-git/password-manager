package org.uy.sdm.pasman.util.crypto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlgorithmType {
    SHA_256 ("SHA-256"),
    AES ("AES");
    private final String name;
}
