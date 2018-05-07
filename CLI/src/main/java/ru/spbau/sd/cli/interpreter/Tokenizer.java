package ru.spbau.sd.cli.interpreter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Tokenizer takes a pipeline string and allows to iterate through its commands,
 * each of which is represented as a list of tokens.
 */
public class Tokenizer implements Iterable<List<String>> {
    private static final char PIPELINE_DELIMITER = '|';
    private static final char ESCAPE_CHARACTER = '\\';
    private static final char VARIABLE_CHARACTER = '$';

    private String line;
    private Environment environment;

    Tokenizer(String line, Environment environment) {
        this.line = line;
        this.environment = environment;
    }

    @Override
    public Iterator<List<String>> iterator() {
        return new PipelineIterator();
    }

    private class PipelineIterator implements Iterator<List<String>> {
        private int pos;

        PipelineIterator() {
            pos = 0;
            wind();
        }

        @Override
        public boolean hasNext() {
            return pos < line.length();
        }

        @Override
        public List<String> next() {
            List<String> tokens = new ArrayList<>();
            while (pos < line.length() && line.charAt(pos) != PIPELINE_DELIMITER) {
                tokens.add(getToken());
            }
            if (pos < line.length() && line.charAt(pos) == PIPELINE_DELIMITER) {
                ++pos;
            }
            return tokens;
        }

        private void wind() {
            while (pos < line.length() && line.charAt(pos) == ' ') {
                ++pos;
            }
        }

        private String getToken() {
            wind();
            StringBuilder tokenBuilder = new StringBuilder();
            while (pos < line.length() && line.charAt(pos) != ' ' &&
                    line.charAt(pos) != PIPELINE_DELIMITER) {
                char cur = line.charAt(pos);
                switch (cur) {
                    case '\'': {
                        ++pos;
                        tokenBuilder.append(getScope('\'', false));
                        break;
                    }
                    case '"': {
                        ++pos;
                        tokenBuilder.append(getScope('"', true));
                        break;
                    }
                    case VARIABLE_CHARACTER: {
                        ++pos;
                        tokenBuilder.append(getVariableValue());
                        break;
                    }
                    default: {
                        tokenBuilder.append(cur);
                        ++pos;
                    }
                }
            }
            wind();
            return tokenBuilder.toString();
        }

        // Reads a quotient scope
        private String getScope(char finishSymbol, boolean substituteVariables) {
            StringBuilder scopeBuilder = new StringBuilder();
            boolean screening = false;
            boolean stop = false;
            while (pos < line.length() && !stop) {
                if (screening) {
                   scopeBuilder.append(line.charAt(pos));
                   screening = false;
                    ++pos;
                } else {
                    char cur = line.charAt(pos);
                    if (cur == ESCAPE_CHARACTER) {
                        screening = true;
                        ++pos;
                    } else if (cur == finishSymbol) {
                        stop = true;
                        ++pos;
                    } else if (substituteVariables && cur == VARIABLE_CHARACTER) {
                        ++pos;
                        scopeBuilder.append(getVariableValue());
                    } else {
                        scopeBuilder.append(cur);
                        ++pos;
                    }
                }
            }
            return scopeBuilder.toString();
        }

        // Reads the variable name and returns its value from the environment
        private String getVariableValue() {
            StringBuilder nameBuilder = new StringBuilder();
            while (pos < line.length() && (Character.isLetterOrDigit(line.charAt(pos))
                    || line.charAt(pos) == '_')) {
                nameBuilder.append(line.charAt(pos++));
            }
            return environment.get(nameBuilder.toString());
        }
    }
}
