public class Methods {
    public static void main(String[] args) {
        int b = 10;
        new Methods().m((char) b);

        System.out.println(test(1, 2, 3, 4));

        System.out.println("Varargs form: ");
        double average = average();
        System.out.println(average);
        average = average(new int[] {});
        System.out.println(average);
        average = average(1);
        System.out.println(average);
        average = average(1, 2, 3, 4);
        System.out.println(average);

        System.out.println();
        System.out.println("Array form: ");
        double averageArray = averageArray(new int[] {});
        System.out.println(averageArray);
        averageArray = averageArray(new int[] {1});
        System.out.println(averageArray);
        averageArray = averageArray(new int[] {1, 2, 3, 4});
        System.out.println(averageArray);
    }

    public static double average(int... values) {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        return values.length == 0? 0 : sum/values.length;
    }

    public static double averageArray(int[] values) {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        return values.length == 0 ? 0 : sum / values.length;
    }

    public static String test(int x, int... y) {
        return x + "" + y.length;
    }


    public void m(char ch) {
        System.out.println("in char");
    }
}
