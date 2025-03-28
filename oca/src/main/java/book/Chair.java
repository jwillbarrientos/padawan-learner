package book;

public class Chair {
    public static void main(String[] args) {
        Person p = new Person();
        try {
            ((Father) p).dance();
        } finally {}
    }
}
class Person {}
class Father extends Person {
    public void dance() throws ClassCastException {}
}