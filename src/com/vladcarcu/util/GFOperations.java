package com.vladcarcu.util;

public class GFOperations {

    public static int add(int a, int b) {
        return a ^ b;
    }

    public static int multiply(byte a, byte b) {
        int aInt = Byte.toUnsignedInt(a);
        int bInt = Byte.toUnsignedInt(b);

        int multiply = aInt * bInt;

        // divide the multiplication result by the binary equivalent 1 0001 1011
        int result = multiply;
        int divisor = 283;
        while (result > 255) {
            int mostSignificantPosition = findFirstBitOne(result);
            int currentDivisor = divisor << (mostSignificantPosition - 8);
            result = add(result, currentDivisor);
        }

        return result;
    }

    public static int findFirstBitOne(int a) {
        int position = 0;
        int result = 0;
        do {
            if (a == 1) {
                result = position;
            }
            a = a >> 1;
            position++;
        } while (a != 0);
        return result;
    }

    public static void main(String[] args) {
        System.out.println((byte) multiply((byte) 212, (byte) 2));
    }
}
