package com.vladcarcu.aes;

import com.vladcarcu.util.DecryptionResult;
import com.vladcarcu.util.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.util.function.Function;

public class AESDecryptTest {

    @Test
    public void decrypt() throws Exception {
        URI contentPath = ClassLoader.getSystemClassLoader().getResource("set1c7.txt").toURI();
        String decodedString = Utils.readFileAndMap(contentPath, Function.identity());
        DecryptionResult result = AESDecrypt.decrypt(decodedString, "YELLOW SUBMARINE".getBytes());

        Assert.assertEquals("I'm back and I'm ringin' the bell ", result.getResultString().split("\n")[0]);
    }
}
