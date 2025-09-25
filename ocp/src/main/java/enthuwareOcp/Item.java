package enthuwareOcp;

import java.util.Arrays;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class Item {
    private String name;
    private String category;
    private double price;

    public Item(String name, String category, double price){
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public String getCategory() {
        return this.category;
    }

    public static void main(String[] args) {
        List<Item> items = Arrays.asList(
                new Item("Pen", "Stationery", 3.0),
                new Item("Pencil", "Stationery", 2.0),
                new Item("Eraser", "Stationery", 1.0),
                new Item("Milk", "Food", 2.0),
                new Item("Eggs", "Food", 3.0)
        );

        ToDoubleFunction<Item> priceF = Item::getPrice; //1

        items.stream()
                .collect(Collectors.groupingBy(Item::getCategory)) //2
                .forEach((a, b)->{
                    double av = b.stream().collect(Collectors.averagingDouble(priceF)); //3
                    System.out.println(a+" : "+av);
                });
    }
}
