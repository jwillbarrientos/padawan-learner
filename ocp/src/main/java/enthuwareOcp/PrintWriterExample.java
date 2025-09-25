package enthuwareOcp;

import java.io.*;

public class PrintWriterExample {
    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(System.out);
        out.print(5); // PrintWriter method
        out.write(String.valueOf(5)); // Writer method
        out.print(2.0); // PrintWriter method
        out.write(String.valueOf(2.0)); // Writer method

        File source = new File("zoo.log");
        try (PrintWriter out2 = new PrintWriter(
                new BufferedWriter(new FileWriter(source)))) {
            out2.print("Today's weather is: ");
            out2.println("Sunny");
            out2.print("Today's temperature at the zoo is: ");
            out2.print(1/3.0);
            out2.println('C');
            out2.format("It has rained 10.12 inches this year");
            out2.println();
            out2.printf("It may rain 21.2 more inches this year");
        }
    }
}
