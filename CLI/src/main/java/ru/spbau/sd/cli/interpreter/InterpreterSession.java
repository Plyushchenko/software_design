package ru.spbau.sd.cli.interpreter;

import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.SimpleStream;

/**
 * The main class of the interpreter for interacting with users.
 */
public class InterpreterSession {
    private Environment environment = new SimpleEnvironment();
    private Parser parser = new Parser(environment);
    private boolean terminated = false;

    /**
     * Runs a command. May terminate the session.
     * @param command the command to run
     * @return the command output
     */
    public String runCommand(String command) {
        ASTElement ast = parser.parseLine(command);
        if (ast == null) {
            return "";
        }
        InputStream input = new SimpleStream();
        SimpleStream output = new SimpleStream();
        ExecutionResult result = ast.execute(input, output);
        String ret = output.read();
        if (result == ExecutionResult.Finish) {
            terminated = true;
        }
        return ret;
    }

    /**
     * Checks if the exit command has been run.
     * @return true if the session has been terminated.
     */
    public boolean isTerminated() {
        return terminated;
    }
}
