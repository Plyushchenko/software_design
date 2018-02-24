package ru.spbau.sd.cli;

public class Interpreter {
    private Environment environment = new SimpleEnvironment();
    private Parser parser = new Parser(environment);
    private boolean terminated = false;

    public String runCommand(String command) {
        ASTElement ast = parser.parseLine(command);
        InputStream input = new SimpleStream();
        SimpleStream output = new SimpleStream();
        ExecutionResult result = ast.execute(input, output);
        String ret = output.read();
        if (result == ExecutionResult.Finish) {
            terminated = true;
        }
        return ret;
    }

    public boolean isTerminated() {
        return terminated;
    }
}
