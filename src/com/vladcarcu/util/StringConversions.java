package com.vladcarcu.util;

import com.vladcarcu.hex.HexToByte;

import java.util.Base64;

public class StringConversions {

    public static String encodeBase64(String input) {
        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes());
        return new String(encodedBytes);
    }

    public static String decodeBase64(String input) {
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        return new String(decodedBytes);
    }

    public static String hexToBase64(String input) {
        byte[] inputBytes = HexToByte.convert(input);
        byte[] encodedBytes = Base64.getEncoder().encode(inputBytes);
        return new String(encodedBytes);
    }

    public static String stringToHex(String input) {
        return HexToByte.reverse(input.getBytes());
    }

    public static String hexToString(String hexString) {
        return new String(HexToByte.convert(hexString));
    }

}
