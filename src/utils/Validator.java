package utils;

import java.time.LocalDate;

public class Validator {
    public static final String PHONE_FORMAT = "^0[35789]\\d{8}$";
    public static final String EMAIL_FORMAT = "^[a-zA-Z0-9+-_.%]+@gmail\\.com$";
    public static final String REGULAR_MEMBER_ID_FORMAT = "^REG\\d{3}$";
    public static final String BOOK_ID_FORMAT = "^BK\\d{3}$";
    public static final String BORROWING_ID_FORMAT = "^BOR\\d{4}$";


    public static String validateBasicString(String src) {
        if (src == null || src.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Input string cannot be null or empty");
        }

        return src.trim();
    }

    public static String validateBasicEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Email cannot be null or empty");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("❌ Email must contain '@'");
        }

        return email.trim();
    }

    public static int validateNumber(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("❌ Value cannot be negative");
        }

        return n;
    }

    public static LocalDate validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("❌ Date cannot be null");
        }

        return date;
    }

    public static boolean checkEmptyString(String source) {
        if (source == null || source.trim().isEmpty()) {
            return true;
        }

        return false;
    }

    // check Id
    public static boolean checkIdLength(String id) {
        if (id.length() != 6) {
            return false;
        }

        return true;
    }

    public static boolean checkIdMember(String id) {
        String temp = id.substring(0, 3);
        if (!temp.equals("PRE") && !temp.equals("REG")) {
            return false;
        }

        for (int i = 3; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkIdPremium(String id) {
        String temp = id.substring(0, 3);
        if (!temp.equals("PRE")) {
            return false;
        }

        temp = id.substring(3);
        for (int i = 0; i < temp.length(); i++) {
            if (!Character.isDigit(temp.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkIdRegular(String id) {
        if (!id.matches(REGULAR_MEMBER_ID_FORMAT)) {
            return false;
        }

        return true;
    }

    public static boolean checkIdBook(String id) {
        if (!id.matches(BOOK_ID_FORMAT)) {
            return false;
        }

        return true;
    }

    public static boolean checkIdBorrowing(String id) {
        if (!id.matches(BORROWING_ID_FORMAT)) {
            return false;
        }

        return true;
    }

    public static boolean checkStringWord(String source) {
        for (int i = 0; i < source.length(); i++) {
            if (!Character.isLetter(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkPhoneNumber(String source) {
        if (!source.matches(PHONE_FORMAT)) {
            return false;
        }

        return true;
    }

    public static boolean checkEmail(String source) {
        if (!source.matches(EMAIL_FORMAT)) {
            return false;
        }

        return true;
    }

    public static boolean checkTitle(String source) {
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
           boolean isValid = Character.isLetter(c) || Character.isDigit(c) || Character.isWhitespace(c) ||
                   c == '.' || c == ',' || c == '_' ||
                   c == '-' || c == ':' || c == '(' ||
                   c == ')' || c == '?' || c == '!';

           if (!isValid) {
               return false;
           }
        }

        return true;
    }

    public static boolean checkYesNo(char source) {
        if (source != 'Y' && source != 'N') {
            return false;
        }

        return true;
    }
}

