package amigosCode.javaBasics;

import java.time.LocalDate;
import java.util.Scanner;

public class ScannerClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What's your name?");

        String userName = scanner.nextLine();
        System.out.println("Hello " + userName);

        System.out.println("How old are you?");
        int userAge = scanner.nextInt();
        System.out.println("You born in " + LocalDate.now().minusYears(userAge).getYear());

        if(userAge >= 18) {
            System.out.println("You are an adult");
        }else{
            System.out.println("You are not an adult");
        }
    }
}