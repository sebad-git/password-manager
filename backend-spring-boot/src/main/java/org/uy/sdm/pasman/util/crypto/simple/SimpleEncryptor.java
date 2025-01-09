package org.uy.sdm.pasman.util.crypto.simple;

import org.uy.sdm.pasman.util.crypto.EncryptionException;

public interface SimpleEncryptor {

    String encrypt(final String content) throws EncryptionException;

}
