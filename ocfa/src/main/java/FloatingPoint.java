import java.util.Arrays;
import java.util.Scanner;

public class FloatingPoint {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};
        int suma = 0;
        for (int i = 0; i < numbers.length; i++) {
            suma = suma + numbers[i];
        }
        System.out.println(suma);

        int[] third = null;
        System.out.println(third);
        System.out.println(" - " + third);
        System.out.println(" - " + Arrays.toString(third));

        String[] chars = new String[4];
        char cha = 97;
        for (char c = 0; c < chars.length; c++) {
            chars[c] = String.valueOf(cha);
            cha++;
        }
        System.out.println(Arrays.toString(chars));

        int[] values = {1, 3, 54, 2, 6, 4, 76, 4, 53, 12};
        int sum = 0;
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                sum = values[i];
                System.out.println("The sum its " + sum);
                continue;
            }
            if (i == 1) {
                sum = values[0] + values[i];
                System.out.println("The sum its " + sum);
                continue;
            }
            sum = (values[i - 1] + values[i - 2]) + values[i];
            System.out.println("The sum its " + sum);
            sum = 0;
        }

        int nextNumber = 0;
        Scanner scanner = new Scanner(System.in);
        int[] valuesxd = new int[10];
        System.out.println("Type the two first ints of your array, and I make the rest");
        System.out.println("First number: ");
        valuesxd[0] = scanner.nextInt();
        System.out.println("Second number: ");
        valuesxd[1] = scanner.nextInt();
        for (int i = 2; i < valuesxd.length; i++) {
            nextNumber = (valuesxd[i - 1] + valuesxd[i - 2]);
            valuesxd[i] = nextNumber;
            nextNumber = 0;
        }

        System.out.println("This is the Array with the For Loop: " + Arrays.toString(valuesxd));

        int[] valuesWhile = new int[10];
        System.out.println("Type the two first ints of your array, and I make the rest");
        System.out.println("First number: ");
        valuesWhile[0] = scanner.nextInt();
        System.out.println("Second number: ");
        valuesWhile[1] = scanner.nextInt();
        int i = 2;
        while (i < valuesWhile.length) {
            nextNumber = (valuesWhile[i - 1] + valuesWhile[i - 2]) + valuesWhile[i];
            valuesWhile[i] = nextNumber;
            nextNumber = 0;
            i++;
        }
        System.out.println("This is the Array with the While Loop: " + Arrays.toString(valuesWhile));
    }
}
