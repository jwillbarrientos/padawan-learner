package enthuwareOcp;

import java.util.ArrayList;
import java.util.Collections;

public class Food implements Comparable<Food> {
    String name;
    int caloriesPerServing;
    public Food(String name, int calories)  {
        this.name = name; this.caloriesPerServing = calories;
    }

    public int compareTo(Food f) {
        return this.name.compareTo(f.name);
    }

    public String toString() {
        return this.name;
    }

    public static void main(String[] args) {
        ArrayList<Food> al = new ArrayList<>();
        Food carrot = new Food("carrot", 1);
        Food limon = new Food("limon", 2);
        Food pizza = new Food("pizza", 3);
        al.add(pizza);
        al.add(limon);
        al.add(carrot);
        System.out.println(al);
        Collections.sort(al);
        System.out.println(al);
    }
}
