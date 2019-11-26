package com.vladcarcu.util;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {

    public static char[][] splitString(String input, int blockSize) {
        var resultLength = input.length() % blockSize == 0 ? input.length() / blockSize : input.length() / blockSize + 1;
        var result = new char[resultLength][];
        for (var i = 0; i < resultLength; i++) {
            var end = Math.min((i + 1) * blockSize, input.length());
            result[i] = input.substring(i * blockSize, end).toCharArray();
        }

        return result;
    }

    public static char[][] transpose(char[][] input) {
        var maxRowLength = 0;
        for (char[] chars : input) {
            if (maxRowLength < chars.length) {
                maxRowLength = chars.length;
            }
        }
        var lastRowLength = input[input.length - 1].length;
        var result = new char[maxRowLength][];
        for (var row = 0; row < lastRowLength; row++) {
            result[row] = new char[input.length];
        }
        for (var row = lastRowLength; row < maxRowLength; row++) {
            result[row] = new char[input.length - 1];
        }

        for (var row = 0; row < input.length; row++) {
            for (var col = 0; col < input[row].length; col++) {
                result[col][row] = input[row][col];
            }
        }
        return result;
    }

    public static String readFileAndMap(URI path, Function<String, String> mapper){
        List<String> content;
        try {
            content = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content.stream()
                .map(mapper)
                .collect(Collectors.joining());
    }
}
