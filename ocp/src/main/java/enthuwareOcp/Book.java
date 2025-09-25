package enthuwareOcp;

import java.util.function.Supplier;

public class Book {
    private String title;
    private Double price;
    public Book(String title, Double price) {
        this.title = title;
        this.price = price;
    }

    public Double getPrice() {
        return this.price;
    }

    public String getTitle() {
        return this.title;
    }

    public static void main(String[] args) {
        Book b1 = new Book("Java in 24 hrs", 30.0);
        Book b2 = new Book("Thinking in Java", null);
        Supplier s1 = b1::getPrice;
        System.out.println(b1.getTitle() + " " + s1.get());
        Supplier s2 = b2::getPrice;
        System.out.println(b2.getTitle() + " " + s2.get());
    }
}
