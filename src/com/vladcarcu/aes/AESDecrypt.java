package com.vladcarcu.aes;

import com.vladcarcu.util.DecryptionResult;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESDecrypt {

    public static DecryptionResult decrypt(String base64EncodedInput, byte[] key) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] inputBytes = Base64.getDecoder().decode(base64EncodedInput);
            return new DecryptionResult(inputBytes, key, cipher.doFinal(inputBytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
