package ru.spbau.sd.cli.interpreter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ru.spbau.sd.cli.interpreter.commands.AssignmentCommand;
import ru.spbau.sd.cli.interpreter.commands.BuiltinCommand;
import ru.spbau.sd.cli.interpreter.commands.Command;
import ru.spbau.sd.cli.interpreter.commands.ExternalCommand;

import java.util.Arrays;
import java.util.Collections;

public class ParserTest {
    @Test
    public void builtinTest() {
        final BuiltinCommand cmd = BuiltinCommand.cat;
        final String arg1 = "file.txt";
        final String arg2 = "software design.txt";
        Parser parser = new Parser(null);
        ASTElement ret = parser.parseLine(String.format("%s %s '%s'", cmd, arg1, arg2));
        Assert.assertEquals(BuiltinCommand.cat, ret.getCommand());
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

        Assert.assertSame(lastElement.getCommand(), BuiltinCommand.echo);
        Assert.assertEquals(lastElement.getArguments(), Collections.EMPTY_LIST);

        ASTElement firstElement = lastElement.getPrevious();
        Assert.assertNotNull(firstElement);
        Assert.assertNull(firstElement.getPrevious());
        Assert.assertEquals(firstElement.getCommand(), BuiltinCommand.echo);
        Assert.assertEquals(firstElement.getArguments(),
                Collections.singletonList(arg));
    }
}
