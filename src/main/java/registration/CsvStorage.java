package registration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvStorage {
    private final Path filePath;

    public CsvStorage(Path filePath) {
        this.filePath = filePath;
    }

    public List<Student> readAll() throws IOException {
        List<Student> students = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return students;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] row = splitCsv(line);
                students.add(Student.fromRow(row));
            }
        }

        return students;
    }

    public void writeAll(List<Student> students) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            for (Student s : students) {
                writer.write(toCsvLine(s.toRow()));
                writer.newLine();
            }
        }
    }

    private String toCsvLine(String[] values) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                out.append(',');
            }
            out.append(escape(values[i]));
        }
        return out.toString();
    }

    // Vienkarsota CSV izbegosana: ja ir komats vai pecina, ieliekam pecinas.
    private String escape(String value) {
        String v = value == null ? "" : value;
        if (v.contains(",") || v.contains("\"")) {
            v = v.replace("\"", "\"\"");
            return "\"" + v + "\"";
        }
        return v;
    }

    // Vienkarsota CSV sadalisana: atbalsta pecinas, bet ne jaunas rindas teksta.
    private String[] splitCsv(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        result.add(current.toString());
        return result.toArray(new String[0]);
    }
}
