package enthuwareOcp;

public class TestClassInterfaces implements I1, I2 {
    public void m1() {
        System.out.println("Hello");
    }

    public static void main(String[] args) {
        TestClassInterfaces tc = new TestClassInterfaces();
        ((I1)tc).m1();
    }
}

interface I1 {
    int VALUE = 1;
    default void m1() {
        System.out.println("Hello from I1");
    }
}

interface I2 {
    int VALUE = 2;
    void m1();
}
