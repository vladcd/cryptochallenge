package com.vladcarcu.xor;

import com.vladcarcu.util.DecryptionResult;

import java.util.HashMap;
import java.util.Map;

public class SingleByteXORBreak {

    public static class XORBreakResult extends DecryptionResult {
        private double score = Double.MIN_VALUE;

        public XORBreakResult(byte[] input, byte key) {
            super(input, ByteArrayXOR.buildSingleByteKey(key, input.length), ByteArrayXOR::xor);
        }

        public double getScore() {
            if (score == Double.MIN_VALUE) {
                score = calculateScore();
            }
            return score;
        }

        @Override
        public String toString() {
            return "Input: " + new String(getInput())
                    + "\nKey: " + new String(getKey())
                    + "\nUncrypted: " + getResultString();
        }

        public byte getByteKey() {
            return getKey()[0];
        }

        private static final Map<String, Double> frequencies = new HashMap<>();
        private static final String NON_ALPHA = "NON_ALPHA";
        private static final String SPACE = "SPACE";

        static {
            frequencies.put(SPACE, 12.5);
            frequencies.put("E", 12.02);
            frequencies.put("T", 9.1);
            frequencies.put("A", 8.12);
            frequencies.put(NON_ALPHA, 7.9);
            frequencies.put("O", 7.68);
            frequencies.put("I", 7.31);
            frequencies.put("N", 6.95);
            frequencies.put("S", 6.28);
            frequencies.put("R", 6.02);
            frequencies.put("H", 5.92);
            frequencies.put("D", 4.32);
            frequencies.put("L", 3.98);
            frequencies.put("U", 2.88);
            frequencies.put("C", 2.71);
            frequencies.put("M", 2.61);
            frequencies.put("F", 2.3);
            frequencies.put("Y", 2.11);
            frequencies.put("W", 2.09);
            frequencies.put("G", 2.03);
            frequencies.put("P", 1.82);
            frequencies.put("B", 1.49);
            frequencies.put("V", 1.11);
            frequencies.put("K", 0.69);
            frequencies.put("X", 0.17);
            frequencies.put("Q", 0.11);
            frequencies.put("J", 0.1);
            frequencies.put("Z", 0.07);
        }

        private double calculateScore() {
            var toJudge = new String(getResult());
            var frequencyTable = buildFrequencyTable(toJudge);
            // the bigger the deviation from the average, the lower the score
            return 100 / frequencyTable.entrySet().stream()
                    .mapToDouble(entry -> {
                        String key = entry.getKey();
                        boolean isSpace = key.replaceAll("\\p{Space}", "").length() == 0;
                        boolean isAlpha = key.replaceAll("\\p{Alpha}", "").length() == 0;
                        boolean isPunct = key.replaceAll("[\\.\\?!,;:'\\[\\](){}\"-]", "").length() == 0;
                        double frequency = 0;
                        if (isSpace) {
                            frequency = frequencies.get(SPACE);
                        } else if (isAlpha) {
                            frequency = frequencies.get(key);
                        } else if (isPunct) {
                            frequency = frequencies.get(NON_ALPHA);
                        } else {
                            frequency = entry.getValue() * 1001;
                        }
                        return Math.abs(frequency - entry.getValue());
                    })
                    .sum();
        }

        private Map<String, Double> buildFrequencyTable(String input) {
            input = input.toUpperCase();
            Map<String, Double> frequencyTable = new HashMap<>();
            for (var i = 0; i < input.length(); i++) {
                frequencyTable.compute(input.substring(i, i + 1), (letter, count) -> count == null ? 1 : count + 1);
            }
            // normalize table
            for (Map.Entry<String, Double> entry : frequencyTable.entrySet()) {
                entry.setValue(entry.getValue() / (double) input.length() * 100);
            }
            return frequencyTable;
        }
    }

    public static XORBreakResult breakIt(String input) {
        Map<Byte, XORBreakResult> keyScores = new HashMap<>();

        for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
            byte b = (byte) i;
            XORBreakResult score = new XORBreakResult(input.getBytes(), b);
            keyScores.put(b, score);
        }

        Map.Entry<Byte, XORBreakResult> mostLikelyEntry = keyScores.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getScore() > 0)
                .max((e1, e2) -> e1.getValue().getScore() >= e2.getValue().getScore() ? 1 : -1)
                .orElseThrow();

        return mostLikelyEntry.getValue();
    }

}
