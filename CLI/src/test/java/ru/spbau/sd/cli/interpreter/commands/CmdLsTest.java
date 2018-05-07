package ru.spbau.sd.cli.interpreter.commands;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import ru.spbau.sd.cli.interpreter.SimpleEnvironment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class CmdLsTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void runTest() throws IOException {
        File subfolder = folder.newFolder("subfolder");
        File file = folder.newFile("file");
        Command lsCommand = new CmdLs(new SimpleEnvironment());
        ru.spbau.sd.cli.interpreter.io.OutputStream outputStream =
                Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        lsCommand.run(Collections.singletonList(folder.getRoot().getAbsolutePath()),
                null, outputStream);
        Mockito.verify(outputStream).write(Mockito.eq("file\n"));
        Mockito.verify(outputStream).write(Mockito.eq("subfolder\n"));

        outputStream = Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        lsCommand.run(Collections.singletonList("abracadabra"), null, outputStream);
        Mockito.verify(outputStream).write(Mockito.eq("ls: cannot access 'abracadabra': No such file or directory"));

        outputStream = Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        lsCommand.run(Collections.singletonList(file.getAbsolutePath()), null, outputStream);
        Mockito.verify(outputStream).write(Mockito.eq("file\n"));

        outputStream = Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        lsCommand.run(Collections.singletonList(subfolder.getAbsolutePath()), null, outputStream);
        Mockito.verifyZeroInteractions(outputStream);
    }
}
