package com.vladcarcu.xor;

import com.vladcarcu.util.StringConversions;
import com.vladcarcu.xor.ByteArrayXOR;
import com.vladcarcu.xor.SingleByteXORBreak;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

public class SingleByteXORTest {

    @Test
    public void breakIt() {
        String input = StringConversions.hexToString("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736");
        SingleByteXORBreak.XORBreakResult score = SingleByteXORBreak.breakIt(input);

        Assert.assertEquals("Cooking MC's like a pound of bacon",
                new String(ByteArrayXOR.xor(input.getBytes(), score.getByteKey())));
    }

    @Test
    public void breakIt_2() {
        String input = StringConversions.hexToString("7b5a4215415d544115415d5015455447414c155c46155f4058455c5b523f");
        SingleByteXORBreak.XORBreakResult score = SingleByteXORBreak.breakIt(input);

        Assert.assertEquals("Now that the party is jumping\n",
                new String(ByteArrayXOR.xor(ByteArrayXOR.buildSingleByteKey(score.getByteKey(), input.getBytes().length), input.getBytes())));
    }

    @Test
    public void detect() throws Exception {
        URI contentPath = ClassLoader.getSystemClassLoader().getResource("xorfile.txt").toURI();
        List<String> rows = Files.readAllLines(Path.of(contentPath))
                .stream()
                .map(row -> StringConversions.hexToString(row))
                .collect(Collectors.toList());
        SingleByteXORBreak.XORBreakResult result = rows.stream()
                .map(row -> {
                    try {
                        return SingleByteXORBreak.breakIt(row);
                    } catch (NoSuchElementException nsee) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .max(Comparator.comparingDouble(SingleByteXORBreak.XORBreakResult::getScore))
                .orElseThrow();

        Assert.assertEquals("Now that the party is jumping\n", result.getResultString());
    }
}
