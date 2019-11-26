package com.vladcarcu.util;

import com.vladcarcu.util.Utils;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void splitArrayInBlocks_whenOk() {
        Assert.assertArrayEquals(new char[][]{new char[]{'a', 'b'}, new char[]{'c'}},
                Utils.splitString("abc", 2));
    }

    @Test
    public void transpose_whenOk() {
        Assert.assertArrayEquals(new char[][]{
                new char[]{'a', 'e'},
                new char[]{'b', 'f'},
                new char[]{'c', 'g'},
                new char[]{'d'}
        }, Utils.transpose(new char[][]{
                new char[]{'a', 'b', 'c', 'd'},
                new char[]{'e', 'f', 'g'}
        }));
    }
}
