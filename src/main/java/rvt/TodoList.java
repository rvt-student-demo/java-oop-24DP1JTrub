package rvt;

import java.util.ArrayList;

public class TodoList {

    private ArrayList<String> tasks;

    // Konstruktors bez parametriem
    public TodoList() {
        this.tasks = new ArrayList<>();
    }

    // Pievieno jaunu uzdevumu
    public void add(String task) {
        this.tasks.add(task);
    }

    // Izdrukā visus uzdevumus ar numuriem
    public void print() {
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println((i + 1) + ": " + this.tasks.get(i));
        }
    }

    // Noņem uzdevumu pēc numura
    public void remove(int number) {
        int index = number - 1; 
        this.tasks.remove(index);
    }
}
