package ru.spbau.sd.cli.interpreter.commands;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ru.spbau.sd.cli.interpreter.Environment;

import java.util.Collections;

public class AssignmentCommandTest {
    @Test
    public void trueIsAssignmentTest() {
        String command = "__a__=bc";
        Assert.assertTrue(AssignmentCommand.isAssignment(command));
    }

    @Test
    public void equalityFirstIsAssignmentTest() {
        String command = "=bc";
        Assert.assertFalse(AssignmentCommand.isAssignment(command));
    }

    @Test
    public void digitIsAssignmentTest() {
        String command = "2a=bc";
        Assert.assertFalse(AssignmentCommand.isAssignment(command));
    }

    @Test
    public void blankIsAssignmentTest() {
        String command = "a=bc ";
        Assert.assertFalse(AssignmentCommand.isAssignment(command));
    }

    @Test
    public void spaceIsAssignmentTest() {
        String command = "a= bc";
        Assert.assertFalse(AssignmentCommand.isAssignment(command));
    }

    @Test
    public void runTest() {
        final String name = "name";
        final String value = "value";
        Environment mockedEnvironment = Mockito.mock(Environment.class);

        AssignmentCommand command = AssignmentCommand.create(
                String.format("%s=%s", name, value), mockedEnvironment);
        command.run(Collections.emptyList(), null, null);
        Mockito.verify(mockedEnvironment).set(name, value);
    }
}
