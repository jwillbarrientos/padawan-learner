package algoritmia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveElementToEnd {
    public static void main(String[] args) {
        int[] array = {2, 1, 2, 2, 2, 3, 4, 2};
        System.out.println(Arrays.toString(moveElementToEnd(array, 2)));
    }

    public static int[] moveElementToEnd(int[] array, int toMove) {
        int i = 0;
        int j = array.length - 1;
        while(i < j) {
            while(array[j] == toMove) j--;
            if(array[i] == toMove) {
                int temporal = array[j];
                array[j] = array[i];
                array[i] = temporal;
                i++;
            }
        }
        return array;
    }
}