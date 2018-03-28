package ru.spbau.sd.cli.interpreter;

import ru.spbau.sd.cli.interpreter.commands.Command;
import ru.spbau.sd.cli.interpreter.io.IOStream;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;
import ru.spbau.sd.cli.interpreter.io.SimpleStream;

import java.util.List;

/**
 * This is an element of a abstract syntax tree (actually list) created during
 * parsing a pipeline.
 */
public class ASTElement {
    private Command command;
    private List<String> arguments;
    private ASTElement previous;

    /**
     * Creates an instance with provided command and arguments.
     * @param command the command
     * @param arguments arguments to pass to command
     */
    ASTElement(Command command, List<String> arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public Command getCommand() {
        return command;
    }

    protected List<String> getArguments() {
        return arguments;
    }

    protected ASTElement getPrevious() {
        return previous;
    }

    /**
     * Sets the previous element link to the provided element.
     * @param previous the previous element of the pipeline.
     */
    public void setPrevious(ASTElement previous) {
        this.previous = previous;
    }

    /**
     * Runs the AST with provided input and output streams. Commands are executed
     * one-by-one, each command except first gets the output of the previous one.
     * @param input the input stream
     * @param output the output stream
     * @return execution result: OK if all commands finished successfully
     * Error if one of them returned error, Finish if an exit command was met.
     */
    public ExecutionResult execute(InputStream input, OutputStream output) {
        InputStream realInput = input;
        if (previous != null) {
            IOStream previousOutput = new SimpleStream();
            ExecutionResult previousResult = previous.execute(input, previousOutput);
            if (previousResult.equals(ExecutionResult.Error) ||
                    previousResult.equals(ExecutionResult.Finish)) {
                output.write(previousOutput.read());
                return previousResult;
            }
            realInput = previousOutput;
        }
        return command.run(arguments, realInput, output);
    }
}
