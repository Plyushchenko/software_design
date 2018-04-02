package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.util.List;

/**
 * Writes its arguments to the output stream. If no arguments provided,
 * copies the input to the output stream.
 */
public class CmdEcho implements Command {
    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream,
                               OutputStream outputStream) {
        if (arguments.isEmpty()) {
            outputStream.write(inputStream.read());
        } else {
            outputStream.write(String.join(" ", arguments));
        }
        return ExecutionResult.OK;
    }
}
