package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.SimpleEnvironment;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * ls command
 */
public class CmdLs implements Command {
    private final Environment environment;

    public CmdLs(Environment environment) {
        this.environment = environment;
    }

    /**
     * Prints content (non-hidden files and folders) of the current folder
     * or a folder at the argument path in alphabetical order.
     * In case of file at the argument path, prints its name;
     *
     * @param arguments    command line arguments
     * @param inputStream  input stream to read
     * @param outputStream output stream to write results
     * @return result type: OK if finished successfully, Error if > 1 arguments were provided
     * or a file/folder at the argument path does not exist.
     * Finish if the interpreter session should be closed.
     */
    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream, OutputStream outputStream) {
        File folder;
        if (arguments.isEmpty()) {
            folder = new File(environment.get(SimpleEnvironment.PWD));
        } else {
            if (arguments.size() != 1) {
                outputStream.write("Please provide 0 or 1 argument");
                return ExecutionResult.Error;
            }
            folder = new File(arguments.get(0));
        }
        if (!folder.exists()) {
            outputStream.write("ls: cannot access '" + folder + "': No such file or directory");
            return ExecutionResult.OK;
        }
        File[] folderContent = folder.listFiles(file -> !file.isHidden());
        if (folderContent == null) {
            outputStream.write(folder.getName() + "\n");
            return ExecutionResult.OK;
        }
        Arrays.sort(folderContent, Comparator.comparing(a -> a.getName().toLowerCase()));
        for (File folderElement: folderContent) {
            outputStream.write(folderElement.getName() + "\n");
        }
        return ExecutionResult.OK;
    }
}
