package ru.spbau.sd.cli.interpreter.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.SimpleEnvironment;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class CmdCdTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void runTest() throws IOException {
        Environment environment = new SimpleEnvironment();
        File sub = folder.newFolder("sub");
        File file = folder.newFile("file");
        Command cdCommand = new CmdCd(environment);
        ru.spbau.sd.cli.interpreter.io.OutputStream outputStream =
                Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        cdCommand.run(Collections.singletonList(file.getAbsolutePath()), null, outputStream);
        Mockito.verify(outputStream).write("cd: " + file.getAbsolutePath() + ": Not a directory");

        outputStream = Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        cdCommand.run(Collections.singletonList(file.getAbsolutePath() + "?"), null, outputStream);
        Mockito.verify(outputStream).write("cd: " + file.getAbsolutePath() + "?: No such file or directory");

        cdCommand.run(Collections.singletonList(folder.getRoot().getAbsolutePath()), null, outputStream);
        cdCommand.run(Collections.singletonList("sub"), null, outputStream);
        Command pwdCommand = new CmdPwd(environment);
        outputStream = Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        pwdCommand.run(Collections.emptyList(), null, outputStream);
        Mockito.verify(outputStream).write(sub.getAbsolutePath());
        outputStream = Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        cdCommand.run(Collections.singletonList("."), null, outputStream);
        pwdCommand.run(Collections.emptyList(), null, outputStream);
        Mockito.verify(outputStream).write(sub.getAbsolutePath());
        outputStream = Mockito.mock(ru.spbau.sd.cli.interpreter.io.OutputStream.class);
        cdCommand.run(Collections.singletonList(".."), null, outputStream);
        pwdCommand.run(Collections.emptyList(), null, outputStream);
        Mockito.verify(outputStream).write(folder.getRoot().getAbsolutePath());
    }
}
