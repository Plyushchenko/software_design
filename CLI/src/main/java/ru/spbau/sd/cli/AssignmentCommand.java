package ru.spbau.sd.cli;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssignmentCommand implements Command {
    private static final Pattern ASSIGNMENT_PATTERN =
            Pattern.compile("^[_a-zA-Z]\\w*=\\w+$");

    private String name;
    private String value;
    private Environment environment;

    AssignmentCommand(String name, String value, Environment environment) {
        this.name = name;
        this.value = value;
        this.environment = environment;
    }

    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream,
                               OutputStream outputStream) {
        environment.set(name, value);
        return ExecutionResult.OK;
    }

    public static boolean isAssignment(String command) {
        Matcher commandMatcher = ASSIGNMENT_PATTERN.matcher(command);
        return commandMatcher.matches();
    }
}
