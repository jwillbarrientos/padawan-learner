package algoritmia;

public class AlanExercise1 {
    public static void main(String args[]) {
        System.out.println("Sum = " + sumNumbers(1, 2));
        System.out.println("Sum = " + sumNumbers(1, (long) 2));
    }
    static String sumNumbers(int a, int b){
        int sum = a+b;
        return "intSum:" + sum;
    }

    static String sumNumbers(long a, long b){
        long sum = a+b;
        return "longSum:" + a+b;
    }
}
