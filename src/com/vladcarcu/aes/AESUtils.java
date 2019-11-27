package com.vladcarcu.aes;

import com.vladcarcu.hex.HexToByte;
import com.vladcarcu.util.DecryptionResult;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class AESUtils {

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

    public static String detect(List<String> hexEncodedCyphers) {
        return (String) hexEncodedCyphers.stream()
                .map(HexToByte::convert)
                .map(row -> new AbstractMap.SimpleEntry(HexToByte.reverse(row), calculateScore(row)))
                .sorted((e1, e2) -> {
                    Double score1 = (Double) e1.getValue();
                    Double score2 = (Double) e2.getValue();
                    return -1 * score1.compareTo(score2);
                })
                .findFirst()
                .orElseThrow()
                .getKey();

    }

    private static double calculateScore(byte[] row) {
        // split row in 16 bytes arrays
        int step = 16;
        if (row.length % step != 0) {
            throw new RuntimeException("Row is not multiple of 16 bytes");
        }

        byte[][] splits = new byte[row.length / step][];
        for (var i = 0; i < splits.length; i++) {
            splits[i] = Arrays.copyOfRange(row, i * step, (i + 1) * step);
        }
        int score = 0;
        for (var splitRow = 0; splitRow < splits.length; splitRow++) {
            for (var splitCol = splitRow + 1; splitCol < splits.length; splitCol++) {
                if (Arrays.equals(splits[splitRow], splits[splitCol])) {
                    score++;
                }
            }
        }
        // normalize score by the number of splits
        return score / (double) splits.length;
    }
}
