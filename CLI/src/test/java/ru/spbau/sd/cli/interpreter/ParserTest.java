package ru.spbau.sd.cli.interpreter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ru.spbau.sd.cli.commands.AssignmentCommand;
import ru.spbau.sd.cli.commands.BuiltinCommand;
import ru.spbau.sd.cli.commands.Command;
import ru.spbau.sd.cli.commands.ExternalCommand;
import ru.spbau.sd.cli.interpreter.ASTElement;
import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.Parser;

import java.util.Arrays;

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
        //TODO implement
    }
}
