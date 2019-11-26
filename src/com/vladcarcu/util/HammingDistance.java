package com.vladcarcu.util;

import java.util.Objects;

public class HammingDistance {

    public static long calculate(byte[] input1, byte[] input2) {
        Objects.requireNonNull(input1);
        Objects.requireNonNull(input2);
        if (input1.length != input2.length) {
            throw new RuntimeException("Could not calculate Hamming distance");
        }

        long result = 0;
        for (int i = 0; i < input1.length; i++) {
            // the number of different bits is the number of bits set to 1 after applying XOR
            byte xor = (byte) (input1[i] ^ input2[i]);
            while (xor != 0) {
                if (xor % 2 == 1) {
                    result++;
                }
                xor >>= 1;
            }
        }

        return result;
    }

}
