package registration;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final String[] HEADERS = {
        "Vards",
        "Uzvards",
        "E-pasts",
        "Personas kods",
        "Registracijas datums"
    };

    public static void main(String[] args) {
        Path csvPath = Path.of("students.csv");
        CsvStorage storage = new CsvStorage(csvPath);
        List<Student> students;
        try {
            students = storage.readAll();
        } catch (IOException e) {
            System.out.println("Neizdevas nolasit CSV failu: " + e.getMessage());
            students = new ArrayList<>();
        }

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Studentu registracijas sistema");
            printHelp();
            while (true) {
                System.out.print("\nKomanda: ");
                String command = scanner.nextLine().trim().toLowerCase();
                if (command.isEmpty()) {
                    continue;
                }
                switch (command) {
                    case "register":
                        handleRegister(scanner, students);
                        persist(storage, students);
                        break;
                    case "show":
                        handleShow(students);
                        break;
                    case "remove":
                        handleRemove(scanner, students);
                        persist(storage, students);
                        break;
                    case "edit":
                        handleEdit(scanner, students);
                        persist(storage, students);
                        break;
                    case "exit":
                        System.out.println("Programma tiek aptureta.");
                        return;
                }
            }
        }
    }

    private static void printHelp() {
        System.out.println("Pieejamas komandas:");
        System.out.println("  register - registret jaunu studentu");
        System.out.println("  show     - radit visus studentus");
        System.out.println("  remove   - dzest studentu pec personas koda");
        System.out.println("  edit     - rediget studentu pec personas koda");
        System.out.println("  exit     - iziet no programmas");
    }

    private static void handleRegister(Scanner scanner, List<Student> students) {
        String firstName = askName(scanner, "Vards");
        String lastName = askName(scanner, "Uzvards");
        String email = askEmail(scanner, students, null);
        String personalCode = askPersonalCode(scanner, students, null);
        LocalDateTime registeredAt = LocalDateTime.now();

        students.add(new Student(firstName, lastName, email, personalCode, registeredAt));
        System.out.println("Students ir pievienots.");
    }

    private static void handleShow(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("Nav registretu studentu.");
            return;
        }
        List<String[]> rows = new ArrayList<>();
        for (Student s : students) {
            rows.add(s.toRow());
        }
        System.out.println(TablePrinter.formatTable(rows, HEADERS));
    }

    private static void handleRemove(Scanner scanner, List<Student> students) {
        System.out.print("Ievadiet personas kodu dzesanai: ");
        String code = scanner.nextLine().trim();
        Student found = findByPersonalCode(students, code);
        if (found == null) {
            System.out.println("Students ar doto personas kodu nav atrasts.");
            return;
        }
        students.remove(found);
        System.out.println("Students ir dzests.");
    }

    private static void handleEdit(Scanner scanner, List<Student> students) {
        System.out.print("Ievadiet personas kodu redigesanai: ");
        String code = scanner.nextLine().trim();
        Student found = findByPersonalCode(students, code);
        if (found == null) {
            System.out.println("Students ar doto personas kodu nav atrasts.");
            return;
        }

        System.out.println("Atstajiet lauku tuksu, ja nevelaties to mainit.");
        String firstName = askOptionalName(scanner, "Jauns vards", found.getFirstName());
        String lastName = askOptionalName(scanner, "Jauns uzvards", found.getLastName());
        String email = askOptionalEmail(scanner, students, found);
        String personalCode = askOptionalPersonalCode(scanner, students, found);

        Student updated = new Student(firstName, lastName, email, personalCode, found.getRegisteredAt());
        students.remove(found);
        students.add(updated);
        System.out.println("Dati atjaunoti.");
    }

    private static String askName(Scanner scanner, String label) {
        while (true) {
            System.out.print(label + ": ");
            String value = scanner.nextLine().trim();
            if (Validator.isValidName(value)) {
                return value;
            }
            System.out.println("Kļūda: drikst tikai burtus, atstarpes vai domuzimes, un vismaz 3 simboli.");
        }
    }

    private static String askOptionalName(Scanner scanner, String label, String current) {
        while (true) {
            System.out.print(label + " [" + current + "]: ");
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                return current;
            }
            if (Validator.isValidName(value)) {
                return value;
            }
            System.out.println("Kļūda: drikst tikai burtus, atstarpes vai domuzimes, un vismaz 3 simboli.");
        }
    }

    private static String askEmail(Scanner scanner, List<Student> students, Student current) {
        while (true) {
            System.out.print("E-pasts: ");
            String value = scanner.nextLine().trim();
            if (!Validator.isValidEmail(value)) {
                System.out.println("Kļūda: neatbilstosa e-pasta forma.");
                continue;
            }
            if (isEmailTaken(students, value, current)) {
                System.out.println("Kļūda: e-pasts jau ir aiznemts.");
                continue;
            }
            return value;
        }
    }

    private static String askOptionalEmail(Scanner scanner, List<Student> students, Student current) {
        while (true) {
            System.out.print("Jauns e-pasts [" + current.getEmail() + "]: ");
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                return current.getEmail();
            }
            if (!Validator.isValidEmail(value)) {
                System.out.println("Kļūda: neatbilstosa e-pasta forma.");
                continue;
            }
            if (isEmailTaken(students, value, current)) {
                System.out.println("Kļūda: e-pasts jau ir aiznemts.");
                continue;
            }
            return value;
        }
    }

    private static String askPersonalCode(Scanner scanner, List<Student> students, Student current) {
        while (true) {
            System.out.print("Personas kods (######-##### vai 11 cipari): ");
            String value = scanner.nextLine().trim();
            if (!Validator.isValidPersonalCode(value)) {
                System.out.println("Kļūda: neatbilstosa personas koda forma.");
                continue;
            }
            if (isPersonalCodeTaken(students, value, current)) {
                System.out.println("Kļūda: personas kods jau eksiste.");
                continue;
            }
            return value;
        }
    }

    private static String askOptionalPersonalCode(Scanner scanner, List<Student> students, Student current) {
        while (true) {
            System.out.print("Jauns personas kods [" + current.getPersonalCode() + "]: ");
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                return current.getPersonalCode();
            }
            if (!Validator.isValidPersonalCode(value)) {
                System.out.println("Kļūda: neatbilstosa personas koda forma.");
                continue;
            }
            if (isPersonalCodeTaken(students, value, current)) {
                System.out.println("Kļūda: personas kods jau eksiste.");
                continue;
            }
            return value;
        }
    }

    private static Student findByPersonalCode(List<Student> students, String personalCode) {
        for (Student s : students) {
            if (s.getPersonalCode().equals(personalCode)) {
                return s;
            }
        }
        return null;
    }

    private static boolean isEmailTaken(List<Student> students, String email, Student current) {
        for (Student s : students) {
            if (current != null && s.getPersonalCode().equals(current.getPersonalCode())) {
                continue;
            }
            if (s.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPersonalCodeTaken(List<Student> students, String personalCode, Student current) {
        for (Student s : students) {
            if (current != null && s.getPersonalCode().equals(current.getPersonalCode())) {
                continue;
            }
            if (s.getPersonalCode().equals(personalCode)) {
                return true;
            }
        }
        return false;
    }

    private static void persist(CsvStorage storage, List<Student> students) {
        try {
            storage.writeAll(students);
        } catch (IOException e) {
            System.out.println("Neizdevas saglabat CSV failu: " + e.getMessage());
        }
    }
}
