package utils;

import javax.sound.sampled.Line;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class InputHelper {
    private Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readStringNonBlank(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            if (Validator.checkEmptyString(input)) {
                System.out.println("❌ Input string cannot be null or empty");
                continue;
            }

            return input.trim();
        }
    }

    public char readYesNo(String message) {
        while (true) {
            String input = readStringNonBlank(message).toUpperCase();
            char temp = input.charAt(0);

            if (!Validator.checkYesNo(temp)) {
                System.out.println("⚠️ Invalid input! Please enter 'y' to continue or 'n' to exit.");
                continue;
            }

            return temp;
        }
    }

    private int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Invalid input! Please enter an integer (e.g: 1, 2, 3,...).");
            }
        }
    }

    public int readIntInRange(String message, int start, int end) {
        while (true) {
            int result = readInt(message);

            if (!(result >= start && result <= end)) {
                System.out.printf("❌ Error: Invalid input! Please enter a number between %d and %d.\n",
                        start, end);
                continue;
            }

            System.out.println("✅ Success: Valid input!");
            return result;
        }
    }

    public String readIdMember(String message) {
        while (true) {
            String input = readStringNonBlank(message).toUpperCase();

            if (!Validator.checkIdLength(input)) {
                System.out.println("❌ Invalid ID format! ID must be exactly 6 characters long " +
                        "(e.g., PRE001 or REG001).");
                continue;
            }

            if (!Validator.checkIdMember(input)) {
                System.out.println("❌ Invalid ID format! Premium ID must start with " +
                        "'PRE' or 'REG' followed by 3 digits (e.g., PRE001, REG001).");
                continue;
            }

            System.out.println("✅ [SUCCESS] Valid ID! Member format checked successfully.");
            return input;
        }
    }

    public String readIdPremiumMember(String message) {
        while (true) {
            String input = readStringNonBlank(message).toUpperCase();

            if (!Validator.checkIdLength(input)) {
                System.out.println("❌ Invalid ID format! ID must be exactly 6 characters long " +
                        "(e.g., PRE001).");
                continue;
            }

            if (!Validator.checkIdPremium(input)) {
                System.out.println("❌ Invalid ID format! Premium ID must start with " +
                        "'PRE' followed by 3 digits (e.g., PRE001).");
                continue;
            }


            System.out.println("💎 Valid ID! Premium Member format checked successfully.");
            return input;
        }
    }

    public String readIdRegularMember(String message) {
        while (true) {
            String input = readStringNonBlank(message).toUpperCase();

            if (!Validator.checkIdRegular(input)) {
                System.out.println("❌ Invalid ID format! Premium ID must start with " +
                        "'REG' followed by 3 digits (e.g., REG001).");
                continue;
            }

            System.out.println("👤 Valid ID! Regular Member format checked successfully.");
            return input;
        }
    }

    public String readIdBook(String message) {
        while (true) {
            String input = readStringNonBlank(message).toUpperCase();

            if (!Validator.checkIdBook(input)) {
                System.out.println("❌ Invalid ID format! Book ID must start with " +
                        "'BK' followed by 3 digits (e.g., BK001).");
                continue;
            }

            System.out.println("🆔 Valid ID! Book format checked successfully.");
            return input;
        }
    }

    public String readIdBorrowing(String message) {
        while (true) {
            String input = readStringNonBlank(message).toUpperCase();

            if (!Validator.checkIdBorrowing(input)) {
                System.out.println("❌ Invalid ID format! Borrowing ID must start with " +
                        "'BOR' followed by 4 digits (e.g., BOR0001).");
            }

            System.out.println("🆔 Valid ID! Borrowing format checked successfully.");
            return input;
        }
    }

    public String readStringWord(String message) {
        while (true) {
            String input = readStringNonBlank(message);

            if (!Validator.checkStringWord(input)) {
                System.out.println("❌ Invalid input! Only letters and spaces are allowed.");
                continue;
            }

            System.out.println("✅ Success: Valid input!");
            return input;
        }
    }

    public String readPhoneNumber(String message) {
        while (true) {
            String input = readStringNonBlank(message);

            if (!Validator.checkPhoneNumber(input)) {
                System.out.println("❌ Invalid phone number format!");
                System.out.println("💡 Please enter a 10-digit number starting with 0 (e.g., 03, 05, " +
                        "07, 08, 09).");
                continue;
            }

            System.out.println("✅ Phone number format is valid!");
            return input;
        }
    }

    public String readEmail(String message) {
        while (true) {
            String input = readStringNonBlank(message);

            if (!Validator.checkEmail(input)) {
                System.out.println("❌ Invalid email format!");
                System.out.println("💡 Please enter a valid Gmail address (e.g., example@gmail.com).");
                continue;
            }

            System.out.println("✅ Email format is valid!");
            return input;
        }
    }

    public String readTitle(String message) {
        while (true) {
            String input = readStringNonBlank(message);

            if (!Validator.checkTitle(input)) {
                System.out.println("❌ Invalid Book Title!");
                System.out.println("⚠️  Title can only contain letters, numbers, spaces, " +
                        "and basic punctuation (. , _ - : ( ) ? !)");
                System.out.println("──────────────────────────────────────────────────────────");
                continue;
            }

            System.out.println("✅ Success: Valid Title!");
            return input;
        }
    }

    public LocalDate readDate(String message) {
        while (true) {
            String date = readStringNonBlank(message);

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withResolverStyle(ResolverStyle.STRICT);
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("❌ The date entered is invalid. Please use the format DD/MM/YYYY " +
                        "(e.g., 08/06/2026).");
            }
        }
    }
}