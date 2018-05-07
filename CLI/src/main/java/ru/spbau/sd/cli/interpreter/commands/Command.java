package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.util.List;

/**
 * An interface representing any command in the interpreter.
 */
public interface Command {
    /**
     * Runs the command.
     * @param arguments command line arguments
     * @param inputStream input stream to read
     * @param outputStream output stream to write results
     * @return result type: OK if finished successfully, Error if an error occured,
     * Finish if the interpreter session should be closed.
     */
    ExecutionResult run(List<String> arguments, InputStream inputStream,
                        OutputStream outputStream);
}
