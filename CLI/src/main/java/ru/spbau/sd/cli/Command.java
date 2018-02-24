package ru.spbau.sd.cli;

import java.util.List;

public interface Command {
    ExecutionResult run(List<String> arguments, InputStream inputStream,
                        OutputStream outputStream);
}
