package enthuwareOcp;

public class ArrayThreads extends Thread {
    class Runner implements Runnable {
        public void run() {
            Thread[] t = new Thread[5];
            for(int i=0; i<t.length; i++) System.out.println(t[i]);
        }
    }

    public static void main(String args[]) throws Exception {
        ArrayThreads tc = new ArrayThreads();
        new Thread( tc.new Runner() ).start();
    }
}
