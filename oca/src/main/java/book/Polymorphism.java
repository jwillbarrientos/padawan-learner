package book;

public class Polymorphism implements BaseInterface1, BaseInterface2, MyInterface{
    public static void main(String[] args) {
        new Polymorphism().getName();
    }
}

interface BaseInterface1 {
    default void getName () {
        System.out.println("Base 1");
    }
}

interface BaseInterface2 {
    default void getName () {
        System.out.println("Base 2");
    }
}

interface MyInterface extends BaseInterface1, BaseInterface2 {
    default void getName() {
        System.out.println("Just me");
    }
}
