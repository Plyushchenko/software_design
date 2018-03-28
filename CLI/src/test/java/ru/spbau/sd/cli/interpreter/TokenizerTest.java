package ru.spbau.sd.cli.interpreter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.Tokenizer;

import java.util.*;

public class TokenizerTest {
    @Test
    public void simpleTest() {
        final String cmd = "cat";
        final String arg = "file.txt";
        Tokenizer tokenizer = new Tokenizer(cmd + " " + arg, null);
        Iterator<List<String>> tokenIterator = tokenizer.iterator();
        List<String> tokens = tokenIterator.next();;
        Assert.assertEquals(Arrays.asList(cmd, arg), tokens);
        Assert.assertFalse(tokenIterator.hasNext());
    }

    @Test
    public void varTest() {
        final String varName = "name";
        final String varValue = "value";

        Environment environmentMock = Mockito.mock(Environment.class);
        Mockito.when(environmentMock.get(varName)).thenReturn(varValue);
        Tokenizer tokenizer = new Tokenizer("$" + varName, environmentMock);
        List<String> tokens = tokenizer.iterator().next();
        Assert.assertEquals(Collections.singletonList(varValue), tokens);
    }

    @Test
    public void singleQuotesTest() {
        final String cmd = "echo";
        final String arg = "a b  $c   $d";
        Tokenizer tokenizer = new Tokenizer(String.format("%s '%s'", cmd, arg), null);
        Assert.assertEquals(Arrays.asList(cmd, arg), tokenizer.iterator().next());
    }

    @Test
    public void doubleQuotesTest() {
        final String input = "\"a  b   $c\"";
        final String expectedOutput = "a  b   d";
        Environment environmentMock = Mockito.mock(Environment.class);
        Mockito.when(environmentMock.get("c")).thenReturn("d");
        Tokenizer tokenizer = new Tokenizer(input, environmentMock);
        Assert.assertEquals(Collections.singletonList(expectedOutput),
                tokenizer.iterator().next());
    }

    @Test
    public void pipelineTest() {
        final String cmd1 = "cat";
        final String arg1 = "in.txt";
        final String cmd2 = "echo";
        final char delimiter = '|';
        final String in = String.format("%s %s %c %s", cmd1, arg1, delimiter, cmd2);
        final List<String> expOut1 = Arrays.asList(cmd1, arg1);
        final List<String> expOut2 = Collections.singletonList(cmd2);
        Tokenizer tokenizer = new Tokenizer(in, null);
        Iterator<List<String>> tokenIterator = tokenizer.iterator();
        Assert.assertEquals(expOut1, tokenIterator.next());
        Assert.assertEquals(expOut2, tokenIterator.next());
        Assert.assertFalse(tokenIterator.hasNext());
    }
}
