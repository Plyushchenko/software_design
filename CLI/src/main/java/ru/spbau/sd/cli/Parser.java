package ru.spbau.sd.cli;

import java.util.List;

public class Parser {
    private Environment environment;

    Parser(Environment environment) {
        this.environment = environment;
    }

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

    public ASTElement parseLine(String line) {
        ASTElement astFinish = null;
        Tokenizer tokenizer = new Tokenizer(line, environment);
        for (List<String> tokens: tokenizer) {
            ASTElement newElem = buildCommand(tokens);
            newElem.setPrevious(astFinish);
            astFinish = newElem;
        }
        return astFinish;
    }
}
