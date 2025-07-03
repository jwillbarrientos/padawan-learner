package book;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class DragonAndUnicorn {
    public static void main(String[] args) {
        java.util.List<Unicorn> unicorns = new java.util.ArrayList<>();
        Set<Integer> numbers = new HashSet<>();
        numbers.add(66);
        numbers.add(10);
        numbers.add(66);
        numbers.add(8);
        for (Integer integer : numbers) System.out.print(integer + ",");
        addUnicorn(unicorns);
        List<String> keywords = new ArrayList<>();
        keywords.add("java");
        printList(keywords);
        List<? super IOException> exceptions = new ArrayList<Exception>();
        //exceptions.add(new Exception()); // DOES NOT COMPILE
        exceptions.add(new IOException());
        exceptions.add(new FileNotFoundException());
        //Unicorn unicorn = unicorns.get(0); // ClassCastException
    }

    public static class LegacyDuck implements Comparable {
        private String name;
        public int compareTo(Object obj) {
            LegacyDuck d = (LegacyDuck) obj; // cast because no generics
            return name.compareTo(d.name);
        }
    }

    public static void printList(List<?> list) {
        for (Object x: list) System.out.println(x);
    }

    private static void addUnicorn(List unicorn) {
        unicorn.add(new Dragon());
    }
    
    static class Dragon {}
    static class Unicorn {}
}

