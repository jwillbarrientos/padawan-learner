package book.singletonPattern;

/*
    Singleton: Design pattern that it is used to have an only instance of a specific class, shared between more classes
 */

public class HayStorage {
    private int quantity = 0;
    private HayStorage() {}

    private static final HayStorage instance = new HayStorage();

    public static HayStorage getInstance() {
        return instance;
    }

    public synchronized void addHayStorage(int amount) {
        quantity += amount;
    }

    public synchronized boolean removeHayStorage(int amount) {
        if(quantity < amount) return false;
        quantity -= amount;
        return true;
    }

    public synchronized int getHayQuantity() {
        return quantity;
    }
}
