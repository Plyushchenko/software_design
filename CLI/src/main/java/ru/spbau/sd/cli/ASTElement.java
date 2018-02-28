package ru.spbau.sd.cli;

import java.util.List;

public class ASTElement {
    private Command command;
    private List<String> arguments;
    private ASTElement previous;

    ASTElement(Command command, List<String> arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public Command getCommand() {
        return command;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public ASTElement getPrevious() {
        return previous;
    }

    public void setPrevious(ASTElement previous) {
        this.previous = previous;
    }

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
