package ru.spbau.sd.cli;

public interface Environment {
    void set(String name, String val);

    String get(String name);
}
