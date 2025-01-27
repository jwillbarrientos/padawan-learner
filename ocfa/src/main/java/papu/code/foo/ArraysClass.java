package papu.code.foo;

import java.util.Arrays;

public class ArraysClass {
    public static void main(String[] args) {
        int [] numbers = {2, 0, 1};
        String [] names = {"Alan", "Jona"};

        /*
        (this it's the same, but longest, and you must specify the size of the array)
        int [] numbers = new int [3];
        numbers[0] = 2;
        numbers[1] = 0;
        numbers[2] = 1;
        */

        System.out.println(Arrays.toString(numbers));
        System.out.println(numbers.length);
        System.out.println(Arrays.toString(names));
        System.out.println(names.length);

        //other example
        int [] numbersxd = {2, 4, 5, 1, 56, 54, 25, 35, 66};
        int one = numbersxd[3];
        int fiftysix = numbersxd[4];
        System.out.println(one);
        System.out.println(fiftysix);

        //if I want grab the last data of the array simply type this
        int lastData = numbersxd[numbersxd.length - 1];
        System.out.println(lastData);
    }
}