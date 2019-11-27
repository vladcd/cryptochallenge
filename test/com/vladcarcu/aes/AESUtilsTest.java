package com.vladcarcu.aes;

import com.vladcarcu.util.DecryptionResult;
import com.vladcarcu.util.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class AESUtilsTest {

    @Test
    public void decrypt() throws Exception {
        URI contentPath = ClassLoader.getSystemClassLoader().getResource("set1c7.txt").toURI();
        String decodedString = Utils.readFileAndMap(contentPath, Function.identity());
        DecryptionResult result = AESUtils.decrypt(decodedString, "YELLOW SUBMARINE".getBytes());

        Assert.assertEquals("I'm back and I'm ringin' the bell ", result.getResultString().split("\n")[0]);
    }

    @Test
    public void detect() throws Exception {
        URI contentPath = ClassLoader.getSystemClassLoader().getResource("set1c8.txt").toURI();
        List<String> rows = Files.readAllLines(Path.of(contentPath));
        Assert.assertEquals("d880619740a8a19b7840a8a31c810a3d08649af70dc06f4fd5d2d69c744cd283e2dd052f6b641dbf9d11b0348542bb5708649af70dc06f4fd5d2d69c744cd2839475c9dfdbc1d46597949d9c7e82bf5a08649af70dc06f4fd5d2d69c744cd28397a93eab8d6aecd566489154789a6b0308649af70dc06f4fd5d2d69c744cd283d403180c98c8f6db1f2a3f9c4040deb0ab51b29933f2c123c58386b06fba186a",
                AESUtils.detect(rows));
    }
}
