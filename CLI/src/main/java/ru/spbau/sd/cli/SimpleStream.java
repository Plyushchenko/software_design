package ru.spbau.sd.cli;

public class SimpleStream implements IOStream {
    private StringBuilder builder;
    private boolean empty;

    SimpleStream() {
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
