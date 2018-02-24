package ru.spbau.sd.cli;

import java.util.Scanner;

public class Shell {
    private static final String PROMPT = ">";

    public static void main(String[] args) {
        Interpreter session = new Interpreter();
        Scanner inputScanner = new Scanner(System.in);
        while (!session.isTerminated()) {
            System.out.print(PROMPT);
            String cmd = inputScanner.nextLine();
            String output = session.runCommand(cmd);
            if (output != null) {
                System.out.println(output);
            }
        }
    }
}
