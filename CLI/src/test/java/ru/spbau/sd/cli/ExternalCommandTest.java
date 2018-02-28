package ru.spbau.sd.cli;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

public class ExternalCommandTest {
    @Test
    public void printfTest() {
        final String cmd = "printf";
        final String arg = "foo";
        final List<String> args = Collections.singletonList(arg);
        ExternalCommand externalCommand = new ExternalCommand(cmd);
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        externalCommand.run(args, null, outputStream);
        Mockito.verify(outputStream).write(arg + "\n");
    }
}
