package ru.spbau.sd.cli;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BuiltInCommandTest {
    private final String content = "test content";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void echoArgTest() {
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        BuiltinCommand.echo.run(Collections.singletonList(content),
                null, outputStream);
        Mockito.verify(outputStream).write(Mockito.eq(content));
    }

    @Test
    public void echoStreamTest() {
        InputStream inputStream = Mockito.mock(InputStream.class);
        Mockito.when(inputStream.read()).thenReturn(content);
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        BuiltinCommand.echo.run(Collections.emptyList(), inputStream, outputStream);
        Mockito.verify(outputStream).write(Mockito.eq(content));
    }

    @Test
    public void catTest() throws IOException {
        File file = folder.newFile();
        Writer writer = new FileWriter(file);
        writer.write(content);
        writer.close();
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        BuiltinCommand.cat.run(Collections.singletonList(file.toPath().toString()),
                null, outputStream);
        Mockito.verify(outputStream).write(Mockito.eq(content + '\n'));
    }

    @Test
    public void exitTest() {
        Assert.assertEquals(ExecutionResult.Finish,
                BuiltinCommand.exit.run(Collections.emptyList(),
                        null, null));
    }

    @Test
    public void pwdTest() {
        String dir = Paths.get(".").toAbsolutePath().normalize().toString();
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        BuiltinCommand.pwd.run(Collections.emptyList(), null, outputStream);
        Mockito.verify(outputStream).write(dir);
    }

    @Test
    public void wcTest() throws IOException {
        File file = folder.newFile();
        Writer writer = new FileWriter(file);
        writer.write(content + '\n');
        writer.write(content + '\n');
        writer.close();
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        BuiltinCommand.wc.run(Collections.singletonList(file.toPath().toString()),
                null, outputStream);
        List<String> expected = Stream.of(2, 4, 2 * (content.length() + 1),
                file.toPath())
                .map(Object::toString)
                .collect(Collectors.toList());
        Mockito.verify(outputStream).write(Mockito.argThat(string ->
            expected.equals(Arrays.asList(string.split("[ |\\n]")))
        ));
    }
}
