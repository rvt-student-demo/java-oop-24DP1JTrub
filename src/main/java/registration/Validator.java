package registration;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L}][\\p{L}\\s-]{2,}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PERSONAL_CODE_PATTERN = Pattern.compile("^(\\d{6}-\\d{5}|\\d{11})$");

    public static boolean isValidName(String value) {
        return value != null && NAME_PATTERN.matcher(value.trim()).matches();
    }

    public static boolean isValidEmail(String value) {
        return value != null && EMAIL_PATTERN.matcher(value.trim()).matches();
    }

    public static boolean isValidPersonalCode(String value) {
        return value != null && PERSONAL_CODE_PATTERN.matcher(value.trim()).matches();
    }
}
