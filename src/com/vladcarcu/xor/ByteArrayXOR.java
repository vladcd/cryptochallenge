package com.vladcarcu.xor;

public class ByteArrayXOR {

    public static byte[] xor(byte[] a, byte b) {
        return xor(a, buildSingleByteKey(b, a.length));
    }

    public static byte[] buildSingleByteKey(byte b, int keyLength) {
        byte[] result = new byte[keyLength];
        for (var i = 0; i < keyLength; i++) {
            result[i] = b;
        }
        return result;
    }

    public static byte[] xor(byte[] a, byte[] b) {
        if (a.length != b.length) {
            throw new RuntimeException("Strings must be equal length");
        }

        byte[] result = new byte[a.length];

        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) (a[i] ^ b[i]);
        }

        return result;
    }

    public static byte[] repeatingKeyXor(byte[] input, byte[] key) {
        byte[] result = new byte[input.length];

        int keyIndex = 0;
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (input[i] ^ key[keyIndex % key.length]);
            keyIndex++;
        }

        return result;
    }

}
