package enthuwareOcp;

interface Boiler {
    public void boil();
    public static void shutdown() {
        System.out.println("shutting down");
    }
}

interface Vaporizer extends Boiler {
    //public default void shutdown() {
    //    System.out.println("shutting down from Vaporizer");
    //}

    //public default void boil() {
    //    System.out.println("Boiling from Vaporizer");
    //}

    public default void vaporize() {
        boil();
        System.out.println("Vaporized!");
    }
}

public class Reactor implements Vaporizer {
    public void boil() {
        System.out.println("Boiling...");
    }

    public static void main(String[] args) {
        Vaporizer v = new Reactor();
        v.vaporize();
        //v.shutdown();
    }
}
