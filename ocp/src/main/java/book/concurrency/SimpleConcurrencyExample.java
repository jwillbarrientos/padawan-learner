package book.concurrency;

public class SimpleConcurrencyExample {
    private static int counter = 0;
    public static void main(String[] args) {
        new Thread(() -> {
            for(int i=0; i<500; i++) SimpleConcurrencyExample.counter++;
        }).start();
        while(SimpleConcurrencyExample.counter<100) {
            System.out.println("Not reached yet");
        }
        System.out.println("Reached!");
    }
}
