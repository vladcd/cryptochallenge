package com.vladcarcu.xor;

import com.vladcarcu.util.DecryptionResult;
import com.vladcarcu.util.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.util.Base64;

public class RepeatingKeyXorBreakTest {

    @Test
    public void test() throws Exception {
        URI contentPath = ClassLoader.getSystemClassLoader().getResource("set1c6.txt").toURI();
        String decodedString = Utils.readFileAndMap(contentPath, (line) -> new String(Base64.getDecoder().decode(line)));
        DecryptionResult result = RepeatingKeyXorBreak.breakIt(decodedString);
        Assert.assertEquals("I'm back and I'm ringin' the ", result.getResultString().split("\n")[0]);
    }
}
