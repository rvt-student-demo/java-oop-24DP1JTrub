package rvt;

import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class TodoList {private ArrayList<String> tasks;
private Path filePath;

public TodoList() {
    this.tasks = new ArrayList<>();
    this.filePath = null;
}

public void loadFromFile(String filename) {
    this.filePath = Paths.get(filename);
    this.tasks.clear();

    if (!Files.exists(this.filePath)) {
        return;
    }

    try {
        for (String line : Files.readAllLines(this.filePath)) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                this.tasks.add(trimmed);
            }
        }
    } catch (IOException e) {
        System.out.println("Neizdevās nolasīt failu: " + e.getMessage());
    }
}

private void saveToFile() {
    if (this.filePath == null) {
        return;
    }

    try {
        Files.write(this.filePath, this.tasks);
    } catch (IOException e) {
        System.out.println("Neizdevās saglabāt failu: " + e.getMessage());
    }
}

public void add(String task) {
    this.tasks.add(task);
    saveToFile();
}

public void print() {
    for (int i = 0; i < this.tasks.size(); i++) {
        System.out.println((i + 1) + ": " + this.tasks.get(i));
    }
}

public void remove(int number) {
    int index = number - 1;
    this.tasks.remove(index);
    saveToFile();
}
}