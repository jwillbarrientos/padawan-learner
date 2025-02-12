package amigosCode.javaBasics;

public class Classxd {
    public static void main(String[] args) {
        Mobile mobileOne = new Mobile("Google", "9 ProXL", 949, false);
        Mobile mobileTwo = new Mobile("Apple", "16 ProMax", 1599.9, true);
        Mobile mobileThree = new Mobile("Samsung", "24 Ultra", 1099.9, true);

        System.out.println("Mobile One");
        System.out.println("Brand: " + mobileOne.brandC);
        System.out.println("Model: " + mobileOne.modelC);
        System.out.println("Price: " + mobileOne.priceC);
        System.out.println("New: " + mobileOne.isNewC);
        System.out.println();

        System.out.println("Mobile Two");
        System.out.println("Brand: " + mobileTwo.brandC);
        System.out.println("Model: " + mobileTwo.modelC);
        System.out.println("Price: " + mobileTwo.priceC);
        System.out.println("New: " + mobileTwo.isNewC);
        System.out.println();

        System.out.println("Mobile Three");
        System.out.println("Brand: " + mobileThree.brandC);
        System.out.println("Model: " + mobileThree.modelC);
        System.out.println("Price: " + mobileThree.priceC);
        System.out.println("New: " + mobileThree.isNewC);
    }
    static class Mobile {
        String brandC;
        String modelC;
        double priceC;
        boolean isNewC;

        Mobile(String brand, String model, double price, boolean isNew) {
            this.brandC = brand;
            this.modelC = model;
            this.priceC = price;
            this.isNewC = isNew;
        }
    }
}