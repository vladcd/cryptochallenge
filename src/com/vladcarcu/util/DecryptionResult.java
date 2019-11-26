package com.vladcarcu.util;

import java.util.function.BiFunction;

public class DecryptionResult {

    private final byte[] key;
    private final byte[] result;
    private final byte[] input;

    public DecryptionResult(byte[] input, byte[] key, BiFunction<byte[], byte[], byte[]> decryptMapper) {
        this.input = input;
        this.key = key;
        this.result = decryptMapper.apply(input, key);
    }

    public DecryptionResult(byte[] input, byte[] key, byte[] result) {
        this.input = input;
        this.key = key;
        this.result = result;
    }

    public byte[] getKey() {
        return key;
    }

    public byte[] getResult() {
        return result;
    }

    public String getResultString() {
        return new String(result);
    }

    public byte[] getInput() {
        return input;
    }
}
