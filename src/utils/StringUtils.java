package utils;

public class StringUtils {
    public static String beautify(String source) {
        String safeString = source.trim();

        safeString = safeString.replaceAll("\\s+", " ");

        safeString = safeString.toLowerCase();

        char[] temp = safeString.toCharArray();
        temp[0] = Character.toUpperCase(temp[0]);

        for (int i = 0; i < temp.length; i++) {
            if (Character.isWhitespace(temp[i])) {
               temp[i + 1] = Character.toUpperCase(temp[i + 1]);
            }
        }

        return new String(temp);
    }
}