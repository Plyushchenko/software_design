package ru.spbau.sd.cli;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This enumeration represents all the commands built in the interpreter.
 */
public enum BuiltinCommand implements Command {
    /**
     * Writes its arguments to the output stream. If no arguments provided,
     * copies the input to the output stream.
     */
    echo {
        @Override
        public ExecutionResult run(List<String> arguments, InputStream inputStream,
                                   OutputStream outputStream) {
            if (arguments.isEmpty()) {
                outputStream.write(inputStream.read());
            } else {
                outputStream.write(String.join(" ", arguments));
            }
            return ExecutionResult.OK;
        }
    },
    /**
     * Tells the interpreter to close session.
     */
    exit {
        @Override
        public ExecutionResult run(List<String> arguments, InputStream inputStream,
                                   OutputStream outputStream) {
            return ExecutionResult.Finish;
        }
    },
    /**
     * Writes path to the current directory to the output stream.
     */
    pwd {
        @Override
        public ExecutionResult run(List<String> arguments, InputStream inputStream,
                                   OutputStream outputStream) {
            Path curPath = Paths.get(".").normalize().toAbsolutePath();
            outputStream.write(curPath.toString());
            return ExecutionResult.OK;
        }
    },
    /**
     * If any arguments are passed, writes content of files with that names to the
     * output stream. Otherwise copies input to output stream.
     */
    cat {
        @Override
        public ExecutionResult run(List<String> arguments, InputStream inputStream,
                                   OutputStream outputStream) {
            if (arguments.isEmpty()) {
                outputStream.write(inputStream.read());
                return ExecutionResult.OK;
            }
            for (String filename: arguments) {
                Path filepath = Paths.get(filename);
                try {
                    Files.lines(filepath)
                            .map(s -> s + '\n')
                            .forEach(outputStream::write);
                } catch (IOException e) {
                    outputStream.write(e.getMessage());
                    return ExecutionResult.Error;
                }
            }
            return ExecutionResult.OK;
        }
    },
    /**
     * Counts lines, words and symbols in files which names are passed as arguments.
     * If no arguments provided, reads from input stream.
     */
    wc {
        private final int STATS_NUMBER = 3;

        @Override
        public ExecutionResult run(List<String> arguments, InputStream inputStream,
                                   OutputStream outputStream) {
            if (arguments.isEmpty()) {
                WCStat counter = new WCStat(inputStream.read());
                outputStream.write(String.format("%d %d %d",
                        counter.getLinesNumber(), counter.getWordsNumber(),
                        counter.getSymbolsNumber()));
            }
            for (String filename: arguments) {
                Path filepath = Paths.get(filename);
                try {
                    WCStat counter = new WCStat();
                    Files.lines(filepath)
                            .map(s -> s + '\n')
                            .map(WCStat::new)
                            .forEach(counter::merge);
                    outputStream.write(String.format("%d %d %d %s\n",
                            counter.getLinesNumber(), counter.getWordsNumber(),
                            counter.getSymbolsNumber(), filename));
                } catch (IOException e) {
                    outputStream.write(e.getMessage());
                    return ExecutionResult.Error;
                }
            }
            return ExecutionResult.OK;
        }

        private long countNonEmpty(String[] strs) {
            return Arrays.stream(strs)
                    .filter(s -> !s.isEmpty())
                    .count();
        }

        class WCStat {
            private long stats[] = new long[STATS_NUMBER];
            private int INDEX_LINES = 0;
            private int INDEX_WORDS = 1;
            private int INDEX_SYMBOLS = 2;

            WCStat() {
                Arrays.fill(stats, 0);
            }

            WCStat(String str) {
                stats[INDEX_LINES] = (' ' + str).split("\n").length;
                stats[INDEX_WORDS] = countNonEmpty(str.split("[ \n\t]"));
                stats[INDEX_SYMBOLS] = str.length();
            }

            private void merge(WCStat other) {
                for (int i = 0; i < STATS_NUMBER; ++i) {
                    stats[i] += other.stats[i];
                }
            }

            private long getLinesNumber() {
                return stats[INDEX_LINES];
            }

            private long getWordsNumber() {
                return stats[INDEX_WORDS];
            }

            private long getSymbolsNumber() {
                return stats[INDEX_SYMBOLS];
            }
        }
    };

    private static Set<String> commands = new HashSet<>(Arrays.stream(values())
                    .map(BuiltinCommand::toString)
                    .collect(Collectors.toList()));

    /**
     * Checks if a command with provided name is built in.
     * @param command the name to check.
     * @return true if such builtin command exists, false if it does not.
     */
    public static boolean exists(String command) {
        return commands.contains(command);
    }

    @Override
    abstract public ExecutionResult run(List<String> arguments,
                                        InputStream inputStream,
                                        OutputStream outputStream);
}
