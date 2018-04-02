package ru.spbau.sd.cli.interpreter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ru.spbau.sd.cli.interpreter.commands.*;

import java.util.Arrays;
import java.util.Collections;

public class ParserTest {
    @Test
    public void builtinTest() {
        final String arg1 = "file.txt";
        final String arg2 = "software design.txt";
        Parser parser = new Parser(null);
        ASTElement ret = parser.parseLine(String.format("cat %s '%s'", arg1, arg2));
        Assert.assertTrue(ret.getCommand() instanceof CmdCat);
        Assert.assertNull(ret.getPrevious());
        Assert.assertEquals(Arrays.asList(arg1, arg2), ret.getArguments());
    }

    @Test
    public void assignmentTest() {
        final String varName = "name";
        final String varValue = "value";
        final String cmd = String.format("%s=%s", varName, varValue);
        Environment environmentMock = Mockito.mock(Environment.class);
        Parser parser = new Parser(environmentMock);
        ASTElement ast = parser.parseLine(cmd);
        Command command = ast.getCommand();
        Assert.assertTrue(command instanceof AssignmentCommand);
        command.run(null, null, null);
        Mockito.verify(environmentMock).set(varName, varValue);
    }

    @Test
    public void externalTest() {
        final String unknownCommand = "nonsense";
        Parser parser = new Parser(null);
        ASTElement ast = parser.parseLine(unknownCommand);
        Assert.assertTrue(ast.getCommand() instanceof ExternalCommand);
    }

    @Test
    public void pipelineTest() {
        final String pipeline = "echo foo | echo";
        final String arg = "foo";
        Parser parser = new Parser(null);
        ASTElement lastElement = parser.parseLine(pipeline);

        Assert.assertTrue(lastElement.getCommand() instanceof CmdEcho);
        Assert.assertEquals(lastElement.getArguments(), Collections.EMPTY_LIST);

        ASTElement firstElement = lastElement.getPrevious();
        Assert.assertNotNull(firstElement);
        Assert.assertNull(firstElement.getPrevious());
        Assert.assertTrue(firstElement.getCommand() instanceof CmdEcho);
        Assert.assertEquals(firstElement.getArguments(),
                Collections.singletonList(arg));
    }
}
