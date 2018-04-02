package ru.spbau.sd.cli.interpreter.commands;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.sd.cli.interpreter.ExecutionResult;

import java.util.Collections;

public class CmdExitTest {
    @Test
    public void runTest() {
        Command exitCommand = new CmdExit();
        Assert.assertEquals(ExecutionResult.Finish,
                exitCommand.run(Collections.emptyList(),
                        null, null));
    }
}
