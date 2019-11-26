package com.vladcarcu.util;

import com.vladcarcu.util.StringConversions;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringConversionsTest {

    @Test
    public void hexToBase64_whenOK() {
        Assert.assertEquals(
                "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t",
                StringConversions.hexToBase64("49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"));
    }

    @Test
    public void stringToHexString_whenOk() {
        assertEquals("4275726e696e672027656d2c20696620796f752061696e277420717569636b20616e64206e696d626c65",
                StringConversions.stringToHex("Burning 'em, if you ain't quick and nimble"));
    }

    @Test
    public void encodeBase64_whenOk() {
        assertEquals("YWJj",
                StringConversions.encodeBase64("abc"));
    }

    @Test
    public void decodeBase64_whenOk() {
        assertEquals("abc",
                StringConversions.decodeBase64("YWJj"));
    }

    @Test
    public void stringToHex_whenOk() {
        assertEquals("617364", StringConversions.stringToHex("asd"));
    }

    @Test
    public void hexToString_whenOk() {
        assertEquals("asd", StringConversions.hexToString("617364"));
    }
}
