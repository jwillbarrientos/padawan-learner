package book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemSample {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String userInput = reader.readLine();
        System.out.println("You entered the following: " + userInput);
    }
}
