package org.uy.sdm.pasman.util.crypto.simple;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.uy.sdm.pasman.util.crypto.AlgorithmType;
import org.uy.sdm.pasman.util.crypto.EncryptionException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
class Sha256 implements SimpleEncryptor {

    @Override
    public String encrypt(String content) throws EncryptionException {
       try {
           if(Strings.isEmpty(content))
               throw new EncryptionException("The content is empty");
           final MessageDigest digest = MessageDigest.getInstance(AlgorithmType.SHA_256.getName());
           final byte[] hash = digest.digest( content.getBytes(StandardCharsets.UTF_8));
           final StringBuilder hexString = new StringBuilder(2 * hash.length);
           for (byte b : hash) {
               String hex = Integer.toHexString(0xff & b);
               if (hex.length() == 1)
                   hexString.append('0');
               hexString.append(hex);
           }
           return hexString.toString();
       }catch (NoSuchAlgorithmException e){
           throw new EncryptionException(e);
       }
    }
}
