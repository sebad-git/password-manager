package org.uy.sdm.pasman.util.crypto.symmetric;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.uy.sdm.pasman.util.crypto.EncryptionException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
class Aes implements SymmetricEncryptor {

    private static final int PWD_ITERATIONS = 65536;

    private static final int SALT_SIZE = 20;

    private static final String KEY_ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;
    private static final String ENCRYPT_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    @Override
    public String encrypt(String password, String content) throws EncryptionException {
        try {
            if(Strings.isEmpty(content))
                throw new EncryptionException("The content is empty");
            final SecureRandom random = new SecureRandom();
            final byte[] saltBytes = new byte[SALT_SIZE];
            random.nextBytes(saltBytes);
            final SecretKeyFactory skf = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);
            //KeyFactory initialization.
            final PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    saltBytes,
                    PWD_ITERATIONS,
                    KEY_SIZE
            );
            final SecretKey secretKey = skf.generateSecret(spec);
            final SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
            //AES initialization.
            final Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //Generate IV.
            final byte[] ivBytes = cipher
                    .getParameters().
                    getParameterSpec(IvParameterSpec.class).getIV();
            final byte[] encryptedText = cipher.doFinal(content.getBytes(CHARSET));
            //The salt is stored in the beginning of the file.
            final byte[] result =
                    new byte[saltBytes.length + ivBytes.length + encryptedText.length];
            System.arraycopy(saltBytes, 0, result, 0, saltBytes.length);
            System.arraycopy(ivBytes, 0, result, saltBytes.length, ivBytes.length);
            System.arraycopy(encryptedText, 0, result, saltBytes.length + ivBytes.length,
                    encryptedText.length);
            //Encode to base64.
            return Base64.getEncoder().encodeToString(result);
        }catch (InvalidKeySpecException | NoSuchPaddingException
                | IllegalBlockSizeException | InvalidParameterSpecException
                | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new EncryptionException(e);
        }
    }

    @Override
    public String decrypt(String password, String encryptedContent) throws EncryptionException {
        try {
            final int SALT_POSITION = SALT_SIZE;
            final int IV_POSITION = 16;
            final int CONTENT_POSITION = SALT_POSITION+IV_POSITION;
            final byte[] result = Base64.getDecoder().decode(encryptedContent);
            //First 20 bytes is the Salt.
            final byte[] saltBytes = new byte[SALT_POSITION];
            System.arraycopy(result, 0, saltBytes, 0, SALT_POSITION);
            //After that the first 16 bytes = are the IV.
            final byte[] vector = new byte[IV_POSITION];
            System.arraycopy(result, SALT_POSITION, vector, 0, IV_POSITION);
            final byte[] encryptTextBytes = new byte[result.length - CONTENT_POSITION];
            System.arraycopy(result, CONTENT_POSITION,
                    encryptTextBytes, 0,
                    result.length - CONTENT_POSITION
            );
            final SecretKeyFactory skf = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);
            final PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(), saltBytes, PWD_ITERATIONS, KEY_SIZE
            );
            final SecretKey secretKey = skf.generateSecret(spec);
            final SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
            //Decrypt the content.
            final Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(vector));
            final byte[] decryptTextBytes = cipher.doFinal(encryptTextBytes);
            //Convert to String UTF8.
            return new String(decryptTextBytes, CHARSET);
        } catch (NoSuchAlgorithmException
                 | InvalidKeySpecException
                 | InvalidAlgorithmParameterException
                 | InvalidKeyException
                 | BadPaddingException
                 | IllegalBlockSizeException
                 | IllegalArgumentException
                 | NoSuchPaddingException e
        ){
            throw new EncryptionException(e);
        }
    }
}
