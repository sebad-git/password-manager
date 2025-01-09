package org.uy.sdm.pasman.util.crypto.simple;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SimpleEncryption {

    public static SimpleEncryptor SHA_256() {
        return new Sha256();
    }
}
