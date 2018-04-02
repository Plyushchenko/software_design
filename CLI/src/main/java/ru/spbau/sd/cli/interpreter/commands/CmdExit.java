package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.util.List;

/**
 * Tells the interpreter to close session.
 */
public class CmdExit implements Command {
    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream,
                               OutputStream outputStream) {
        return ExecutionResult.Finish;
    }
}
