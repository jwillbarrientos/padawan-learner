package book.FactoryPattern;

public class FoodFactory {
    public static Food getFood(String animalName) {
        switch (animalName) {
            case "zebra":
                return new Hay(100);
            case "rabbit":
                return new Pellets(5);
            case "goat":
                return new Pellets(30);
            case "polar bear":
                return new Fish(10);
        }

        //Good practice to throw an exception if no matching subclass could be found
        throw new UnsupportedOperationException("Unsupported animal: " + animalName);
    }
}

class ZooKeeper {
    public static void main(String[] args) {
        final Food food = FoodFactory.getFood("polar bear");
        food.consumed();
    }
}
