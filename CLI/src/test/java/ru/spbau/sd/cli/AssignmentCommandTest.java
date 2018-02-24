package ru.spbau.sd.cli;

import org.junit.Assert;
import org.junit.Test;

public class AssignmentCommandTest {
    @Test
    public void trueIsAssignmentTest() {
        String command = "__a__=bc";
        Assert.assertEquals(true, AssignmentCommand.isAssignment(command));
    }

    @Test
    public void equalityFirstIsAssignmentTest() {
        String command = "=bc";
        Assert.assertEquals(false, AssignmentCommand.isAssignment(command));
    }

    @Test
    public void digitIsAssignmentTest() {
        String command = "2a=bc";
        Assert.assertEquals(false, AssignmentCommand.isAssignment(command));
    }

    @Test
    public void blankIsAssignmentTest() {
        String command = "a=bc ";
        Assert.assertEquals(false, AssignmentCommand.isAssignment(command));
    }

    @Test
    public void spaceIsAssignmentTest() {
        String command = "a= bc";
        Assert.assertEquals(false, AssignmentCommand.isAssignment(command));
    }
}
