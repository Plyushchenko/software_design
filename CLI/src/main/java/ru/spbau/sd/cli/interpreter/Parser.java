package ru.spbau.sd.cli.interpreter;

import ru.spbau.sd.cli.interpreter.commands.AssignmentCommand;
import ru.spbau.sd.cli.interpreter.commands.BuiltinCommand;
import ru.spbau.sd.cli.interpreter.commands.Command;
import ru.spbau.sd.cli.interpreter.commands.ExternalCommand;

import java.util.List;

/**
 * Parser takes a pipeline in string form and builds an abstract syntax tree
 * (actually list).
 */
public class Parser {
    private Environment environment;

    Parser(Environment environment) {
        this.environment = environment;
    }

    /*
    Creates an AST node from a pipeline step represented in the form of a list
    of tokens. The first node is interpreted as command name, the rest - as
    arguments.
     */
    private ASTElement buildCommand(List<String> tokens) {
        Command command;
        String commandToken = tokens.get(0);
        if (BuiltinCommand.exists(commandToken)) {
            command = BuiltinCommand.valueOf(commandToken);
        } else if (AssignmentCommand.isAssignment(commandToken)) {
            String[] parts = tokens.get(0).split("=");
            command = new AssignmentCommand(parts[0], parts[1], environment);
        } else {
            command = new ExternalCommand(commandToken);
        }
        return new ASTElement(command, tokens.subList(1, tokens.size()));
    }

    /**
     * Makes an AST from pipeline string.
     * @param line a string with commands
     * @return the last element of the built AST
     */
    public ASTElement parseLine(String line) {
        ASTElement astFinish = null;
        Tokenizer tokenizer = new Tokenizer(line, environment);
        for (List<String> tokens: tokenizer) {
            if (tokens.isEmpty()) {
                continue;
            }
            ASTElement newElem = buildCommand(tokens);
            newElem.setPrevious(astFinish);
            astFinish = newElem;
        }
        return astFinish;
    }
}
