package rvt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class pasutijumuVesture {
    public static void main(String[] args) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new File("data/orders.csv"))) {
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
        double summ = 0;
        while (sc.hasNextLine()) {
            String row = sc.nextLine();
            String[] parts = row.split(",");
            String id = parts[0];
            String name = parts[1];
            String product = parts[2];
            Integer quantity = Integer.valueOf(parts[3]);
            Double price = Double.valueOf(parts[4]);
            System.out.println("Pasūtījums #" + id + ": " + name + " pasūtīja " + quantity + " x " + product + " ("
                     + price + " EUR) -> Kopā: " + (price * quantity) + " EUR");
            summ += price * quantity;
        }
        System.out.println("Kopēja pasūtījuma summa: " + summ + " EUR");
        sc.close();
        }
    }
}
