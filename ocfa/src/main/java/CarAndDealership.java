import java.util.ArrayList;
public class CarAndDealership {
    public static void main(String[] args) {
        Dealership xd = new Dealership();
        Car mercedes = new Car();
        mercedes.make = new String("mercedesbenz");
        mercedes.model = "bm15";
        mercedes.year = 2007;
        xd.addCar(mercedes);
        Car mercedes2 = new Car();
        mercedes2.make = "mercedesbenz";
        mercedes2.model = "dfsa";
        mercedes2.year = 2010;
        xd.addCar(mercedes2);
        mercedes = new Car();
        mercedes.make = "toyota";
        mercedes.model = "ajsdik";
        mercedes.year = 1999;
        xd.addCar(mercedes);
        xd.printCars();
        System.out.println(mercedes.year);
        mercedes = xd.cars.get(0);
    }
    static class Car {
        public String make;
        public String model;
        public int year;
    }
    static class Dealership {
        public ArrayList<Car> cars = new ArrayList<>();
        public void addCar(CarAndDealership.Car c) { cars.add(c); }
        public void removeCar(CarAndDealership.Car c) { cars.remove(c); }
        public void printCars() {
            for(CarAndDealership.Car c : cars) {
                System.out.println("Make: "+c.make+" Model: "+c.model+" Year: "+c.year);
            }
        }
    }
}
