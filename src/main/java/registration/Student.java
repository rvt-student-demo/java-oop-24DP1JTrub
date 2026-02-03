package registration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Student {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String personalCode;
    private final LocalDateTime registeredAt;

    public Student(String firstName, String lastName, String email, String personalCode, LocalDateTime registeredAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.personalCode = personalCode;
        this.registeredAt = registeredAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public String[] toRow() {
        return new String[] {
            firstName,
            lastName,
            email,
            personalCode,
            registeredAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        };
    }

    public static Student fromRow(String[] row) {
        if (row.length < 5) {
            throw new IllegalArgumentException("CSV row has too few columns");
        }
        return new Student(
            row[0],
            row[1],
            row[2],
            row[3],
            LocalDateTime.parse(row[4], DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }
}
