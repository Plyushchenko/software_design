package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the Command interface allowing to run an external program.
 */
public class ExternalCommand implements Command {
    private String program;

    public ExternalCommand(String program) {
        this.program = program;
    }

    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream,
                               OutputStream outputStream) {
        List<String> processArguments = new ArrayList<>();
        processArguments.add(program);
        processArguments.addAll(arguments);
        ProcessBuilder runningBuilder = new ProcessBuilder(processArguments);
        runningBuilder.redirectErrorStream(true);

        try {
            Process runningProcess = runningBuilder.start();
            java.io.InputStream processOutputStream = runningProcess.getInputStream();
            InputStreamReader outputReader = new InputStreamReader(processOutputStream);
            BufferedReader stdoutReader = new BufferedReader(outputReader);
            int exitCode = runningProcess.waitFor();

            stdoutReader.lines()
                    .map(s -> s + '\n')
                    .forEach(outputStream::write);
            if (exitCode != 0) {
                return ExecutionResult.Error;
            }
            return ExecutionResult.OK;
        } catch (IOException | InterruptedException e) {
            outputStream.write(e.getMessage());
            return ExecutionResult.Error;
        }
    }
}
