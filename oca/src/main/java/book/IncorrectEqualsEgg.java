package book;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class IncorrectEqualsEgg {
    public static void main(String[] args) {
        int[][] multiArr = new int[2][3];
        for (int i = 0; i < multiArr.length; i++) {
            for (int j = 0; j < multiArr[i].length; j++) {
                multiArr[i][j] = i + j;
            }
        }
        System.out.println(Arrays.toString(multiArr[1]));
        System.out.println(Arrays.deepToString(multiArr));

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        System.out.println(integers);
        integers.clear();
        System.out.println(integers);

        DateTimeFormatter d1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse("2057-01-29", d1);
        System.out.println(date1);

        // DateTimeFormatter d2 = DateTimeFormatter.ofPattern("yyyy");
        // LocalDate date2 = LocalDate.parse("2057", d2);
        // System.out.println(date2);

        BankAccount b1 = new BankAccount();
        b1.acctNumber = "0023490";
        b1.acctType = 4;
        ArrayList <BankAccount> list = new ArrayList<BankAccount>();
        list.add(b1);
        BankAccount b2 = new BankAccount();
        b2.acctNumber = "0023490";
        b2.acctType = 4;
        System.out.println(list.contains(b2));

        int[] numbers = {5, 7, 1, 1, 2, 3, 22};
        System.out.println(nonConstructibleChange(numbers));
    }

    interface BaseInterface1 {
        static void getName() {
            System.out.println("Base 1");
        }
    }

    interface BaseInterface2 {
        default void getName() {
            System.out.println("Base 2");
        }
    }

    interface BaseInterface3 {
        default void getName() {
            System.out.println("Base 3");
        }
    }

    interface MyInterface extends BaseInterface1, BaseInterface2, BaseInterface3 {
        default void getName() {
            BaseInterface1.getName();
            BaseInterface2.super.getName();
            getName();
        }
    }

    public static int nonConstructibleChange(int[] coins) {
        Arrays.sort(coins);
        int currentMin = 0;
        if(coins.length == 0) return 1;
        for(int i = 0; i < coins.length; i++) {
            if(coins[i] <= (currentMin += 1)) {
                currentMin += coins[i];
            }
            if(coins[i] > (currentMin += 1)) {
                return currentMin + 1;
            }
        }
        return currentMin;
    }
}

class BankAccount {
    String acctNumber;
    int acctType;
    public boolean equals(Object obj) {
        BankAccount b = (BankAccount) obj;
        if(obj != null) {
            return (acctNumber.equals(b.acctNumber) && acctType == b.acctType);
        } else {
            return false;
        }
    }
}
