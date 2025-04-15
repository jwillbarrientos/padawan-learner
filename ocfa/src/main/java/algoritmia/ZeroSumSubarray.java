package algoritmia;

public class ZeroSumSubarray {
    public static void main(String[] args) {
        int[] array = {0};
        System.out.println(zeroSumSubarray(array));
    }
    public static boolean zeroSumSubarray(int[] nums) {
        for(int i = 0; i < nums.length; i++) {
            int sum = nums[i];
            for(int j = i + 1; j < nums.length; j++) {
                if(sum == 0) return true;
                sum = sum + nums[j];
                if(sum == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
