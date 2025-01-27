public class PassByValueExample {
    public static void main(String[] args) {
        int num = 5;
        System.out.println("Original num: " + num); // Output: Original num: 5
        modifyValue(num);
        System.out.println("After modifyValue, num: " + num); // Output: After modifyValue, num: 5

        int[] arr = {1, 2, 3};
        System.out.println("Original arr[0]: " + arr[0]); // Output: Original arr[0]: 1
        modifyReference(arr);
        System.out.println("After modifyReference, arr[0]: " + arr[0]); // Output: After modifyReference, arr[0]: 10
    }

    public static void modifyValue(int value) {
        value = 10;
    }

    public static void modifyReference(int[] array) {
        array[0] = 10;
    }
}
