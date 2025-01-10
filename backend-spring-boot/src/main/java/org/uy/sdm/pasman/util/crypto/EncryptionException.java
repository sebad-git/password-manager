package org.uy.sdm.pasman.util.crypto;

public class EncryptionException extends RuntimeException {

    public EncryptionException(Exception e){
        super(e);
    }

    public EncryptionException(final String message){
        super(message);
    }
}
