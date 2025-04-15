package algoritmia;

import java.util.Arrays;

public class MissingNumbers {
    public static void main(String[] args) {
        int[] array = {3, 4, 5};
        System.out.println(Arrays.toString(missingNumbers(array)));
    }
    public static int[] missingNumbers(int[] nums) {
        int[] missingNumbers = new int[2];
        int k = 1;
        Arrays.sort(nums);
        boolean flag = false;
        if(nums.length == 0)
            return new int[] {1, 2};
        if(nums.length == 1)
            switch(nums[0]) {
                case 1 :
                    return new int[] {2, 3};
                case 2 :
                    return new int[] {1, 3};
                case 3 :
                    return new int[] {1, 2};
            }
        for(int i = 0, j = 0; i < nums.length + 1; i++) {
            if(missingNumbers[1] != 0)
                break;
            if(i >= (nums.length - 1)) {
                missingNumbers[j] = nums[nums.length - 1] + k;
                flag = true;
                k++;
                j++;
            }
            if(nums[0] > 1) {
                switch(nums[0]) {
                    case 2 :
                        missingNumbers[0] = 1;
                        break;
                    case 3 :
                        missingNumbers[0] = 1;
                        missingNumbers[1] = 2;
                        return missingNumbers;
                }
            }
            if(flag == false && i < nums.length && ((nums[i] + 1) != nums[i + 1])) {
                missingNumbers[j] = nums[i] + 1;
                j++;
            }
        }
        return missingNumbers;
    }
}
