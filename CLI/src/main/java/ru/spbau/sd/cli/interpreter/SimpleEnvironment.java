package ru.spbau.sd.cli.interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of the Environment interface based on a hash table.
 */
public class SimpleEnvironment implements Environment {
    public static final String HOME = "HOME";
    public static final String PWD = "PWD";
    private final Map<String, String> vars;

    public SimpleEnvironment() {
        vars = new HashMap<>();
        set("HOME", System.getProperty("user.home"));
        set("PWD", System.getProperty("user.dir"));
    }

    public void set(String name, String val) {
        vars.put(name, val);
    }

    public String get(String name) {
        return vars.getOrDefault(name, "");
    }
}
