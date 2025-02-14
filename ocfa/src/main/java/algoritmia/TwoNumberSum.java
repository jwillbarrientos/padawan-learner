package algoritmia;

import java.util.Arrays;

public class TwoNumberSum {
    public static void main(String[] args) {
        int[] array = {3, 5, -4, 8, 11, 1, -1, 6};
        int targetSum = 10;
        int[] newArray = twoNumberSum(array, 10);
        System.out.println(Arrays.toString(newArray));
    }
    public static int[] twoNumberSum(int[] array, int targetSum) {
        for(int i = 0; i < array.length; i++) {
            for(int j = i + 1; j < array.length; j++) {
                if((array[i] + array[j]) == targetSum) {
                    int[] newArray = {array[i], array[j]};
                    return newArray;
                }
            }
        }
        int[] emptyArray = {};
        return emptyArray;
    }
}
