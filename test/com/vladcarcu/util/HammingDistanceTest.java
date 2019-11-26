package com.vladcarcu.util;

import com.vladcarcu.util.HammingDistance;
import org.junit.Assert;
import org.junit.Test;

public class HammingDistanceTest {

    @Test(expected = RuntimeException.class)
    public void calculate_whenDifferentLength() {
        HammingDistance.calculate("a".getBytes(), "ab".getBytes());
    }

    @Test
    public void calculate_whenOk() {
        Assert.assertEquals(37L,
                HammingDistance.calculate("this is a test".getBytes(), "wokka wokka!!!".getBytes()));
    }

}
