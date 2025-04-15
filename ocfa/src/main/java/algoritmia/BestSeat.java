package algoritmia;

public class BestSeat {
    public static void main(String[] args) {
        int[] array = {1, 0, 1, 0, 0, 0, 1};
        System.out.println(bestSeat(array));
    }
    public static int bestSeat(int[] seats) {
        int i = 0;
        int indexCountZeros = 0;
        int countZeros = 0;
        int maxCountZeros = 0;
        int countZerosFinal = 0;
        while(i < seats.length) {
            while(seats[i] == 0) {
                countZeros++;
                i++;
                indexCountZeros = i - 1;
            }
            if(countZeros > maxCountZeros)
                maxCountZeros = countZeros;
            countZeros = 0;
            i++;
            if(i == seats.length && maxCountZeros != 0) {
                for(int j = 0; j < seats.length; j++) {
                    while(seats[j] == 0) {
                        countZerosFinal++;
                        j++;
                    }
                    if(countZerosFinal == maxCountZeros) {
                        maxCountZeros = countZerosFinal;
                        indexCountZeros = j - 1;
                        break;
                    }
                    countZerosFinal = 0;
                }
                return indexCountZeros - (maxCountZeros/2);
            }
        }
        return -1;
    }
}
