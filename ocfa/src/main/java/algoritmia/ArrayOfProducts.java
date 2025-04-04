package algoritmia;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayOfProducts {
    public static void main(String[] args) {
        int[] array = {4, 4};
        ArrayList<Integer> preArray = new ArrayList<>();
        int n = 0;
        for(int i = 0; i < array.length; i++) {
            int j = 0;
            boolean flag = false;
            while(j < array.length) {
                while(j != i && j < array.length) {
                    if (!flag) {
                        n = array[j];
                        j++;
                        flag = true;
                        if(array.length == 2)
                            preArray.add(n);
                        continue;
                    }
                    n = n * array[j];
                    j++;
                    if (j == array.length)
                        preArray.add(n);
                }
                j++;
                if(j == array.length)
                    preArray.add(n);
            }
        }
        int[] finalArray = new int[array.length];
        for(int i = 0; i < array.length; i++) {
            finalArray[i] = preArray.get(i);
        }
        System.out.println(Arrays.toString(finalArray));
    }
}
