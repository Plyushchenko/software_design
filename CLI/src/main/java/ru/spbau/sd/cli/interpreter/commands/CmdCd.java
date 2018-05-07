package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.SimpleEnvironment;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * cd command
 */
public class CmdCd implements Command {
    private final Environment environment;

    public CmdCd(Environment environment) {
        this.environment = environment;
    }

    /**
     * Sets the current folder as the '/home' folder or as a folder extracted from the argument.
     * @param arguments    command line arguments
     * @param inputStream  input stream to read
     * @param outputStream output stream to write results
     * @return result type: OK if finished successfully, Error if > 1 arguments were provided
     * or a folder at the argument path does not exist.
     * Finish if the interpreter session should be closed.
     */
    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream, OutputStream outputStream) {
        String folderAsString;
        if (arguments.isEmpty()) {
            folderAsString = System.getProperty("user.home");
        } else {
            if (arguments.size() != 1) {
                outputStream.write("Please provide 0 or 1 argument");
                return ExecutionResult.Error;
            }
            File file = new File(environment.get(SimpleEnvironment.PWD) + File.separator + arguments.get(0));
            if (!file.exists()) {
                outputStream.write("cd: " + arguments.get(0) + ": No such file or directory");
                return ExecutionResult.Error;
            }
            if (!file.isDirectory()) {
                outputStream.write("cd: " + arguments.get(0) + ": Not a directory");
                return ExecutionResult.Error;
            }
            try {
                folderAsString = file.getCanonicalPath();
            } catch (IOException e) {
                outputStream.write(e.getMessage());
                return ExecutionResult.Error;
            }
        }
        environment.set(SimpleEnvironment.PWD, folderAsString);
        return ExecutionResult.OK;
    }
}
