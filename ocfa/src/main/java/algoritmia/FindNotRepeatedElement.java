package algoritmia;

import java.util.HashMap;
import java.util.Map;

public class FindNotRepeatedElement {
    public static void main(String[] args) {

        int[] arr = null;
//        arr = new int[] {2, 2, 3, 3, 1, 3, 5, 6, 4, 5, 4};
        arr = new int[] {6,4,3,3,2,6,5,9,2,4,5};
//        Arrays.sort(arr);

//        int nonDuplicate = findNonDuplicate(arr);
        int nonDuplicate = findNonDuplicateOnUnsortedArrayWithTwoLoopsUsingMap(arr);
        System.out.println(nonDuplicate);
    }

//    public static int findNonDuplicate(int[] arr) {
//        for (int i = 0; i < arr.length; i++) {
//            boolean duplicateFound = false;
//            for (int j = 0; j < arr.length; j++) {
//                if (arr[i] == arr[j] && j != i) {
//                    duplicateFound = true;
//                    break;
//                }
//            }
//            if (!duplicateFound) {
//                return arr[i];
//            }
//        }
//        return -1;
//    }

   // public static int findNonDuplicateOnSortedArrayWithOneLoop(int[] arr) {
   //     for (int i = 0; i < arr.length; i++) {
   //         if (i != 0 && i != arr.length - 1 && arr[i] != arr[i - 1]) {
   //             if (arr[i] != arr[i + 1]) {
   //                 return arr[i];
   //             }
   //         }
   //         if (i == 0 && arr[i] != arr[i + 1]) {
   //             return arr[i];
   //         }
   //         if (i == arr.length - 1 && arr[i] != arr[i - 1]) {
   //             return arr[i];
   //         }
   //     }
   //     return -1;
   // }

    public static int findNonDuplicateOnUnsortedArrayWithTwoLoopsUsingMap(int[] arr) {
        Map<Integer, Integer> arrVal_count = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            if (!arrVal_count.containsKey(arr[i])) {
                arrVal_count.put(arr[i], 0);
            }
            int oldCount = arrVal_count.get(arr[i]);
            arrVal_count.put(arr[i], oldCount + 1);
        }
//        for (Map.Entry<Integer, Integer> entry : arrVal_count.entrySet()) {
//            if (entry.getValue() == 1) {
//                return entry.getKey();
//            }
//        }

        for (Integer key : arrVal_count.keySet()) {
            if (arrVal_count.get(key) == 1) {
                return key;
            }
        }

//        return arrVal_count.entrySet().stream()
//                .filter(e->e.getValue()==1)
//                .arrVal_count(Map.Entry::getKey)
//                .findFirst()
//                .orElseGet(()-> -1);

        return -1;
    }
}