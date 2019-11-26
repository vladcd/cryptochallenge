package com.vladcarcu.xor;

import com.vladcarcu.util.DecryptionResult;
import com.vladcarcu.util.HammingDistance;

import java.util.*;

import static com.vladcarcu.util.Utils.splitString;
import static com.vladcarcu.util.Utils.transpose;

public class RepeatingKeyXorBreak {

    public static DecryptionResult breakIt(String input) {
        Map<Integer, Double> hammingDistances = new HashMap<>();
        var inputBytes = input.getBytes();
        int minKeySize = 2;
        int maxKeySize = 40;
        for (var i = minKeySize; i <= maxKeySize; i++) {
            if (inputBytes.length < i * 4) {
                break;
            }
            var ranges = new byte[][]{Arrays.copyOfRange(inputBytes, 0, i),
                    Arrays.copyOfRange(inputBytes, i, i * 2),
                    Arrays.copyOfRange(inputBytes, i * 2, i * 3),
                    Arrays.copyOfRange(inputBytes, i * 3, i * 4)};
            List<Double> distances = new ArrayList<>();
            for (var first = 0; first < ranges.length - 1; first++) {
                for (var second = first + 1; second < ranges.length; second++) {
                    distances.add(Double.valueOf(HammingDistance.calculate(ranges[first], ranges[second])));
                }
            }

            hammingDistances.put(i,
                    distances.stream()
                            .mapToDouble(Double::doubleValue)
                            .average()
                            .orElseThrow()
                            / i);
        }

        var keySize = hammingDistances.entrySet().stream()
                .sorted((e1, e2) -> e1.getValue() > e2.getValue() ? 1 : -1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();

        char[][] transposedSplitInput = transpose(splitString(input, keySize));
        char[][] transposedResults = new char[transposedSplitInput.length][];
        byte[] decryptKey = new byte[keySize];
        for (var row = 0; row < transposedSplitInput.length; row++) {
            try {
                String stringRow = new String(transposedSplitInput[row]);
                SingleByteXORBreak.XORBreakResult xorBreakResult = SingleByteXORBreak.breakIt(stringRow);
                transposedResults[row] = xorBreakResult.getResultString().toCharArray();
                decryptKey[row] = xorBreakResult.getByteKey();
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        StringBuilder builder = new StringBuilder();
        char[][] result = transpose(transposedResults);
        for (char[] row : result) {
            builder.append(new String(row)).append("\n");
        }
        return new DecryptionResult(inputBytes, decryptKey, builder.toString().getBytes());
    }
}
