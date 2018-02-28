package ru.spbau.sd.cli;

import org.junit.Assert;
import org.junit.Test;

public class InterpreterTest {
    private final String content = "content";

    @Test
    public void echoTest() {
        Interpreter interpreter = new Interpreter();
        Assert.assertEquals(content, interpreter.runCommand("echo " + content));
    }

    @Test
    public void exitTest() {
        Interpreter interpreter = new Interpreter();
        Assert.assertFalse(interpreter.isTerminated());
        interpreter.runCommand("exit");
        Assert.assertTrue(interpreter.isTerminated());
    }
}
