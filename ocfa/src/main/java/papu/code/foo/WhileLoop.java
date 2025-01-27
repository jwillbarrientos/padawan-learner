package papu.code.foo;

public class WhileLoop {
    public static void main (String[] args) {
        //While loop
        System.out.println("While loop");
        int count = 0;

        while(count <= 20) {
            System.out.println("Count " + count);
            count++;
        }

        //Do while loop, no matter want, will be executed at least once
        System.out.println();
        System.out.println("Do while loop");
        int countxd = 21;

        do {
            System.out.println("Count " + countxd);
            count++;
        }
        while(countxd <= 20);
    }
}
