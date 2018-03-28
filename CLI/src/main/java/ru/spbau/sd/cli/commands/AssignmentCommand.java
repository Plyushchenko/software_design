package ru.spbau.sd.cli.commands;

import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.io.InputStream;
import ru.spbau.sd.cli.io.OutputStream;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a command that allows to assign a value to a variable.
 */
public class AssignmentCommand implements Command {
    private static final Pattern ASSIGNMENT_PATTERN =
            Pattern.compile("^[_a-zA-Z]\\w*=\\w+$");

    private String name;
    private String value;
    private Environment environment;

    /**
     * Creates an instance with provided variable name and value and environment.
     * @param name the name for assignment
     * @param value the value to assign
     * @param environment the environment witch stores variables
     */
    public AssignmentCommand(String name, String value, Environment environment) {
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

    /**
     * Checks whether a string represents an assignment command.
     * @param command a command in string form.
     * @return true if this string is an assignment command, false otherwise.
     */
    public static boolean isAssignment(String command) {
        Matcher commandMatcher = ASSIGNMENT_PATTERN.matcher(command);
        return commandMatcher.matches();
    }
}
