package com.vladcarcu.hex;

public class HexToByte {

    public static byte[] convert(String input) {
        if (input.length() % 2 == 1) {
            throw new RuntimeException("Invalid hex string provided");
        }

        byte[] resultBytes = new byte[input.length() / 2];
        for (int i = 0; i < resultBytes.length; i++) {
            int indexInString = i * 2;
            resultBytes[i] = Integer.valueOf(input.substring(indexInString, indexInString + 2), 16).byteValue();
        }

        return resultBytes;
    }

    public static String reverse(byte[] input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length; i++) {
            result.append(Integer.toHexString(Integer.valueOf((input[i] & 240) >> 4)))
                    .append(Integer.toHexString(Integer.valueOf(input[i] & 15)));
        }

        return result.toString();
    }

}
