package rvt;

import java.util.ArrayList;

public class Box {

    private double capacity;
    private ArrayList<Packable> items;

    public Box(double capacity) {
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    public double weight() {
    double total = 0;

    for (Packable item : items) {
        total += item.weight();
    }

    return total;
}
    public void add(Packable item) {

    if (weight() + item.weight() <= capacity) {
        items.add(item);
    }
}

    @Override
    public String toString() {
        return "Box: " + items.size() + " items, total weight " + weight() + " kg";
    }

}