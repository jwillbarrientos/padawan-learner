package amigosCode.javaBasics;

import java.util.Arrays;

public class Loops {
    public static void main (String[] args) {
        int [] numbers = {1, 2, 3, 4, 5};
        String [] names = {"Jona", "Alan", "Deivi"};

        //tip: simply type (variable name).for, and intelliJ gonna to autocomplete, in this case, for example, numbers.for
        //Enhanced for loop
        for (int number : numbers) {
            System.out.println(number);
        }
        for (String name : names) {
            System.out.println(name);
        }

        //Common loop
        for (int i = 0; i < numbers.length; i++) {
            System.out.println(numbers[i]);
        }
        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i]);
        }

        //Common loop(reverse)
        for (int i = numbers.length - 1; i >= 0; i--) {
            System.out.println(numbers[i]);
        }
        for (int i = names.length - 1; i >= 0; i--) {
            System.out.println(names[i]);
        }

        //Advanced
        Arrays.stream(numbers).forEach(System.out::println);
        Arrays.stream(names).forEach(System.out::println);
    }
}