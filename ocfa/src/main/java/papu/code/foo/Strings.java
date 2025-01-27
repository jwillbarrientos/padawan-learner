package papu.code.foo;

import org.w3c.dom.ls.LSOutput;

import java.time.LocalDate;

public class Strings {
    public static void main (String[] args) {
        String name = new String("JonathanPapu");

        System.out.println(name.toUpperCase());
        System.out.println(name.toLowerCase());
        System.out.println(name.charAt(0));
        System.out.println(name.contains("papu"));
        System.out.println(name.contains("Papu"));
        System.out.println(name.equals("Papu"));
        System.out.println(name.equals("JonathanPapu"));

        LocalDate month = LocalDate.now();
        System.out.println(month.getMonth());
    }
}