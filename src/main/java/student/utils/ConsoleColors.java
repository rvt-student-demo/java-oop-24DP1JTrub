package student.utils;

public enum ConsoleColors {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m");

    public final String code;

    ConsoleColors(String code) {
        this.code = code;
    }
}

