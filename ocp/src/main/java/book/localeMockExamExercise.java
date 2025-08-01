package book;

import java.util.Locale;
import java.util.ResourceBundle;

public class localeMockExamExercise {
    public static void main(String[] args) {
        Locale fr = new Locale("fr");
        Locale.setDefault(new Locale("en", "US"));
        ResourceBundle b = ResourceBundle.getBundle("book.Dolphins", fr);
        System.out.println(b.getString("name"));
        System.out.println(b.getString("age"));
    }
}
