package utils;

import java.util.Scanner;

public class ConsoleHelper {
    private Scanner scanner;

    public ConsoleHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public void pause() {
        System.out.print("👉 Press Enter to continue...");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
        }
    }
}