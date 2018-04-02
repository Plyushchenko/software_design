package ru.spbau.sd.cli.interpreter.commands;

import org.junit.Test;
import org.mockito.Mockito;
import ru.spbau.sd.cli.interpreter.io.InputStream;

import java.util.Collections;

public class CmdEchoTest {
    private final String content = "test content";

    @Test
    public void argTest() {
        Command echoCommand = new CmdEcho();
        ru.spbau.sd.cli.interpreter.io.OutputStream outputStream =
                Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        echoCommand.run(Collections.singletonList(content),
                null, outputStream);
        Mockito.verify(outputStream).write(Mockito.eq(content));
    }

    @Test
    public void streamTest() {
        Command echoCommand = new CmdEcho();
        ru.spbau.sd.cli.interpreter.io.InputStream inputStream =
                Mockito.mock(InputStream.class);
        Mockito.when(inputStream.read()).thenReturn(content);
        ru.spbau.sd.cli.interpreter.io.OutputStream outputStream =
                Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        echoCommand.run(Collections.emptyList(), inputStream, outputStream);
        Mockito.verify(outputStream).write(Mockito.eq(content));
    }
}
