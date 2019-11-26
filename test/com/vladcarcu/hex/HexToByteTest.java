package com.vladcarcu.hex;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HexToByteTest {

    @Test(expected = RuntimeException.class)
    public void convert_whenInvalid() {
        HexToByte.convert("a");
    }

    @Test
    public void convert_whenOK() {
        assertEquals(
                "I'm killing your brain like a poisonous mushroom",
                new String(HexToByte.convert("49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d")));
    }

    @Test
    public void reverse_whenOk() {
        assertEquals("746865206b696420646f6e277420706c6179",
                HexToByte.reverse(new byte[]{116, 104, 101, 32, 107, 105, 100, 32, 100, 111,
                        110, 39, 116, 32, 112, 108, 97, 121}));
    }
}
