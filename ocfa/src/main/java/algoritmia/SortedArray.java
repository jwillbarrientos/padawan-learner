package algoritmia;

public class SortedArray {
    public static void main(String[] args) {
        int [] oldArray = {8, 5, 2, 9, 5, 6, 3};
        int temporal = 0;
        for(int i = oldArray.length - 1; i >= 0; i--) {
            for(int j = i - 1; j >= 0; j--) {
                if(oldArray[i] < oldArray[j]) {
                    temporal = oldArray[j];
                    oldArray[j] = oldArray[i];
                    oldArray[i] = temporal;
                }
            }
        }
        for (int i : oldArray) {
            System.out.println(i);
        }
    }
}
