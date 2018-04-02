package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Writes path to the current directory to the output stream.
 */
public class CmdPwd implements Command {
    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream,
                               OutputStream outputStream) {
        Path curPath = Paths.get(".").normalize().toAbsolutePath();
        outputStream.write(curPath.toString());
        return ExecutionResult.OK;
    }
}
