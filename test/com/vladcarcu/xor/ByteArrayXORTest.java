package com.vladcarcu.xor;

import com.vladcarcu.hex.HexToByte;
import com.vladcarcu.xor.ByteArrayXOR;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ByteArrayXORTest {

    @Test(expected = RuntimeException.class)
    public void xor_whenInvalid() {
        ByteArrayXOR.xor(new byte[3], new byte[1]);
    }

    @Test
    public void xor_whenValid() {
        assertArrayEquals(
                new byte[]{116, 104, 101, 32, 107, 105, 100, 32, 100, 111,
                        110, 39, 116, 32, 112, 108, 97, 121},
                ByteArrayXOR.xor(HexToByte.convert("1c0111001f010100061a024b53535009181c"),
                        HexToByte.convert("686974207468652062756c6c277320657965"))
        );
    }

    @Test
    public void repeatingKeyXor_whenValid() {
        assertEquals(
                "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f",
                HexToByte.reverse(ByteArrayXOR.repeatingKeyXor(
                        ("Burning 'em, if you ain't quick and nimble\n" +
                                "I go crazy when I hear a cymbal").getBytes(),
                        "ICE".getBytes()
                ))
        );
    }
}
