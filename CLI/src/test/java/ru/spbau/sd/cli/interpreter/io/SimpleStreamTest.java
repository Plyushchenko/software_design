package ru.spbau.sd.cli.interpreter.io;

import org.junit.Assert;
import org.junit.Test;

public class SimpleStreamTest {
    private final String testStr1 = "foo";
    private final String testStr2 = "bar";

    @Test
    public void readTest() {
        SimpleStream stream = new SimpleStream();
        stream.write(testStr1);
        Assert.assertEquals(testStr1, stream.read());
        Assert.assertNull(stream.read());
    }

    @Test
    public void mergeTest() {
        SimpleStream stream = new SimpleStream();
        stream.write(testStr1);
        stream.write(testStr2);
        Assert.assertEquals(testStr1 + testStr2, stream.read());
    }
}
