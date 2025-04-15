package algoritmia;

import java.util.ArrayList;
import java.util.Arrays;

public class MergeOverlappingIntervals {
    public static void main(String[] args) {
        int[][] intervals = {{2, 3}, {4, 5}, {6, 7}, {8, 9}, {1, 10}};
        System.out.println(Arrays.deepToString(mergeOverlappingIntervals(intervals)));
    }
    public static int[][] mergeOverlappingIntervals(int[][] intervals) {
        ArrayList<int[]> arrayList = new ArrayList<>();
        sortIntervals(intervals);
        int[] overlappingIntervals = new int[0];
        boolean  overlapped = false;
        int max = 0;
        for(int i = 0; i <= intervals.length - 1; i++) {
            int n = intervals[i][0];
            while(i < intervals.length - 1 && itsOverlapping(intervals[i], intervals[i + 1])) {
                if (intervals[max][1] < intervals[i + 1][1]) {
                    overlappingIntervals = new int[] {n, intervals[i + 1][1]};
                } else {
                    max = i;
                    overlappingIntervals = new int[] {n, intervals[max][1]};
                }
                overlapped = true;
                i++;
            }
            if(overlapped) {
                arrayList.add(overlappingIntervals);
                overlapped = false;
                continue;
            }
            if(intervals[max][1] > intervals[i][1])
                if(containsArray(arrayList, intervals[max])) {
                    continue;
                }
            arrayList.add(intervals[i]);
        }
        return convertToMatrix(arrayList);
    }

    public static boolean containsArray(ArrayList<int[]> arrayList, int[] interval) {
        for (int[] i : arrayList) {
            if ((i[0] == interval[0]) && (i[1] == interval[1]))
                return true;
        }
        return false;
    }

    public static boolean itsOverlapping(int[] interval1, int[] interval2) {
        return interval1[1] >= interval2[0];
    }

    public static int[][] convertToMatrix(ArrayList<int[]> arrayList) {
        int[][] overlappingIntervalsArray = new int[arrayList.size()][2];
        for(int i = 0; i < arrayList.size(); i++) {
            overlappingIntervalsArray[i] = arrayList.get(i);
        }
        return overlappingIntervalsArray;
    }

    public static void sortIntervals(int[][] intervals) {
        int[] temporal;
        for(int i = intervals.length - 1; i >= 0; i--) {
            for(int j = i - 1; j >= 0; j--) {
                if(intervals[i][0] < intervals[j][0]) {
                    temporal = intervals[j];
                    intervals[j] = intervals[i];
                    intervals[i] = temporal;
                }
            }
        }
    }
}