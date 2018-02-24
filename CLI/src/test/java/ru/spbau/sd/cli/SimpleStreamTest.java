package ru.spbau.sd.cli;

import org.junit.Assert;
import org.junit.Test;

public class SimpleStreamTest {
    final String testStr1 = "foo";
    final String testStr2 = "bar";

    @Test
    public void testRead() {
        SimpleStream stream = new SimpleStream();
        stream.write(testStr1);
        Assert.assertEquals(testStr1, stream.read());
        Assert.assertEquals(null, stream.read());
    }

    @Test
    public void testMerge() {
        SimpleStream stream = new SimpleStream();
        stream.write(testStr1);
        stream.write(testStr2);
        Assert.assertEquals(testStr1 + testStr2, stream.read());
    }
}
