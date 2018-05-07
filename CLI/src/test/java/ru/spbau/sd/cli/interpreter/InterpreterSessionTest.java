package ru.spbau.sd.cli.interpreter;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.sd.cli.interpreter.InterpreterSession;

public class InterpreterSessionTest {
    private final String content = "content";

    @Test
    public void echoTest() {
        InterpreterSession interpreter = new InterpreterSession();
        Assert.assertEquals(content, interpreter.runCommand("echo " + content));
    }

    @Test
    public void exitTest() {
        InterpreterSession interpreter = new InterpreterSession();
        Assert.assertFalse(interpreter.isTerminated());
        interpreter.runCommand("exit");
        Assert.assertTrue(interpreter.isTerminated());
    }
}
