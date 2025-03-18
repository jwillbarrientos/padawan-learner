package algoritmia;

public class MonotonicArray {
    public static void main(String[] args) {
        int[] array = {1, 1 , 3, 4, 5, 6, -1};
        System.out.println(isMonotonic(array));
    }

    public static boolean isMonotonic(int[] array) {
        int i = 0;
        while(i < array.length - 1) {
            if(array[array.length - 1] > array[0]) {
                if(array[i + 1] < array[i]) return false;
            } else {
                if(array[i + 1] > array[i]) return false;
            }
            i++;
        }
        return true;
    }
}
