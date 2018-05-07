package ru.spbau.sd.cli.interpreter;

/**
 * An environment stores variables as a mapping from the name to the value.
 * The default value for any name before setting is an empty string.
 */
public interface Environment {
    void set(String name, String val);

    String get(String name);
}
