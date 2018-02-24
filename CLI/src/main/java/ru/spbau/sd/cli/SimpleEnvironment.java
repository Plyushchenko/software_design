package ru.spbau.sd.cli;

import java.util.HashMap;
import java.util.Map;

public class SimpleEnvironment implements Environment {
    private Map<String, String> vars = new HashMap<>();

    public void set(String name, String val) {
        vars.put(name, val);
    }

    public String get(String name) {
        return vars.getOrDefault(name, "");
    }
}
