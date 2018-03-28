package ru.spbau.sd.cli.interpreter.io;

/**
 * An implementation of IOStream, in which the read method returns all the data
 * that has been passed to the write method since the last read.
 */
public class SimpleStream implements IOStream {
    private StringBuilder builder;
    private boolean empty;

    public SimpleStream() {
        builder = new StringBuilder();
        empty = true;
    }

    @Override
    public String read() {
        if (empty) {
            return null;
        }
        empty = true;
        return builder.toString();
    }

    @Override
    public void write(String str) {
        builder.append(str);
        empty = false;
    }
}
