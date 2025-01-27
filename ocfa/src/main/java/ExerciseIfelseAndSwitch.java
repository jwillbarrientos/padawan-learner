import java.util.Scanner;

public class ExerciseIfelseAndSwitch {
    public static void main(String[] args) {
//        args[0] // "arg1"
//        args[1] // "arg2"

        System.out.println("recibi " + args.length + " argumentos");

        for (int i = 0; i < args.length; i++) {
            System.out.println("arg " + i + ": " + args[i]);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Now, type your number");
//        int i = scanner.nextInt();
        int i = Integer.parseInt(args[0]);

        System.out.println("Number: " + i);
        oddOrEven(i);
        System.out.println("Its even or not?");
        System.out.println(oddOrEvenTernary(i));

        if (i < 1 || i > 5) {
            System.out.println("no me jodas, pone bien tu numero");
            return;
        }
        int j = 0;
        switch (i) {
            case 5:
                j = j + i--;
            case 4:
                j = j + i--;
            case 3:
                j = j + i--;
            case 2:
                j = j + i--;
            case 1:
                j = j + i--;
        }
        System.out.println("tu numero magico es: " + j);


        ifsAnidados(scanner);
        ifsNoAnidados(scanner);

    }

    private static void ifsAnidados(Scanner scanner) {
        int i;
        System.out.println("Now, type your number");
        i = scanner.nextInt();
        if (i % 2 == 0 && i % 3 == 0 && i % 5 == 0) {
            System.out.println("235");
        } else {
            if (i % 2 == 0 && i % 3 == 0) {
                System.out.println("23");
            } else {
                if (i % 2 == 0) {
                    System.out.println("Its even");
                } else {
                    if (i % 3 == 0) {
                        System.out.println("Its divisible for three");
                    } else {
                        if (i % 5 == 0) {
                            System.out.println("Its divisible for five");
                        } else {
                            System.out.println("Unknow");
                        }
                    }
                }
            }
        }
    }


    private static void ifsNoAnidados(Scanner scanner) {
        int i;
        System.out.println("Now, type your number");
        i = scanner.nextInt();
        boolean cumpleMiCondicion = i % 2 == 0 && i % 3 == 0 && i % 5 == 0;
        if (cumpleMiCondicion) {
            System.out.println("235");
            return;
        }

        if (i % 2 == 0 && i % 3 == 0) { // si cumple mi con dicion.....,
            System.out.println("23");
            return;
        }

        if (i % 2 == 0) {
            System.out.println("Its even");
            return;
        }

        if (i % 3 == 0) {
            System.out.println("Its divisible for three");
            return;
        }

        if (i % 5 == 0) {
            System.out.println("Its divisible for five");
        }
    }

    static void oddOrEven(int number) {
        if (number % 2 == 0) {
            System.out.println("The number is even");
        } else {
            System.out.println("The number is odd");
        }
    }

    public static boolean oddOrEvenTernary(int number) {
        return number % 2 == 0;
    }
}