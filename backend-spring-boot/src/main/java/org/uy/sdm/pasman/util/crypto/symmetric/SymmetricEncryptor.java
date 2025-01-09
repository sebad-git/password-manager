package org.uy.sdm.pasman.util.crypto.symmetric;

import org.uy.sdm.pasman.util.crypto.EncryptionException;

public interface SymmetricEncryptor {

    String encrypt(final String password, final String content) throws EncryptionException;
    String decrypt(String password, String encryptedContent) throws EncryptionException;

}
