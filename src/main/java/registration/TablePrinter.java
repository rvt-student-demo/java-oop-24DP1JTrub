package registration;

import java.util.ArrayList;
import java.util.List;

public class TablePrinter {
    public static String formatTable(List<String[]> rows, String[] headers) {
        List<String[]> allRows = new ArrayList<>();
        allRows.add(headers);
        allRows.addAll(rows);

        int[] widths = new int[headers.length];
        for (String[] row : allRows) {
            for (int i = 0; i < headers.length; i++) {
                String cell = i < row.length ? row[i] : "";
                widths[i] = Math.max(widths[i], cell.length());
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append(border(widths));
        builder.append(rowLine(headers, widths));
        builder.append(border(widths));
        for (String[] row : rows) {
            builder.append(rowLine(row, widths));
        }
        builder.append(border(widths));
        return builder.toString();
    }

    private static String border(int[] widths) {
        StringBuilder builder = new StringBuilder();
        builder.append('+');
        for (int w : widths) {
            builder.append("-").append("-".repeat(w)).append("-").append('+');
        }
        builder.append('\n');
        return builder.toString();
    }

    private static String rowLine(String[] row, int[] widths) {
        StringBuilder builder = new StringBuilder();
        builder.append('|');
        for (int i = 0; i < widths.length; i++) {
            String cell = i < row.length ? row[i] : "";
            builder.append(' ').append(padRight(cell, widths[i])).append(' ').append('|');
        }
        builder.append('\n');
        return builder.toString();
    }

    private static String padRight(String value, int width) {
        if (value.length() >= width) {
            return value;
        }
        return value + " ".repeat(width - value.length());
    }
}