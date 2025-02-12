package book;

import java.util.Scanner;

public class ExerciseLoops {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your number: ");
        int numberFor = scanner.nextInt();
        int numberWhile = numberFor;
        int numberDo = numberWhile;
        int j = 0;
        System.out.println("Your magic number is: ");
        for (int i = numberFor; i > 0; i--) {
            j = j + numberFor--;
        }
        System.out.println("For Loop: " + j);


        j = 0;
        while (numberWhile > 0) {
            j = j + numberWhile--;
        }
        System.out.println("While Loop: " + j);

        j = 0;
        do {
            j = j + numberDo--;
        } while (numberDo > 0);
        System.out.println("Do While Loop: " + j);
    }
}
