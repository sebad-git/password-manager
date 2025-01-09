package org.uy.sdm.pasman.util.crypto.symmetric;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SymmetricEncryption {

    public static SymmetricEncryptor AES() {
        return new Aes();
    }

}
