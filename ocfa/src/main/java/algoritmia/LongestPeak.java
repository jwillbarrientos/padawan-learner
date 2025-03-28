package algoritmia;

import java.util.ArrayList;

public class LongestPeak {
    public static void main(String[] args) {
        int[] array1 = {1, 2, 3, 3, 4, 0, 10, 6, 5, -1, -3, 2, 3};
        System.out.println(longestPeakMethod(array1));
        int[] array2 = {1, 1, 1, 2, 3, 10, 12, -3, -3, 2, 3, 45, 800, 99, 98, 0, -1, -1, 2, 3, 4, 5, 0, -1, -1};
        System.out.println(longestPeakMethod(array2));
    }
    public static int longestPeakMethod(int[] array) {
        ArrayList<ArrayList<Integer>> arrayList = new ArrayList<>();
        int i = 0;
        while(i < array.length) {
            ArrayList<Integer> subArrayList = new ArrayList<>();
            boolean flag1 = false;
            boolean flag2 = false;
            while (i != array.length - 1 && array[i + 1] > array[i]) {
                flag1 = true;
                subArrayList.add(array[i]);
                i++;
            }
            if (flag1) {
                while ((i != array.length - 1 && array[i] > array[i + 1])) {
                    flag2 = true;
                    subArrayList.add(array[i]);
                    i++;
                }
                subArrayList.add(array[i]);
            }
            if(flag1 && flag2) {
                arrayList.add(subArrayList);
                continue;
            }
            i++;
        }
        int longestPeak = 0;
        for (ArrayList<Integer> integers : arrayList)
            if (integers.size() > longestPeak) longestPeak = integers.size();
        return longestPeak;
    }
}
