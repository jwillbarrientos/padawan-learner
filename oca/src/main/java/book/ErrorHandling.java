package book;

public class ErrorHandling {
    public static void main(String[] args) {
        try {
            myMethod();
        } catch (StackOverflowError s) {
            for (int i = 0; i <2; i++) {
                System.out.println(i);
            }
        }
    }
    public static void myMethod() {
        myMethod();
    }
}
