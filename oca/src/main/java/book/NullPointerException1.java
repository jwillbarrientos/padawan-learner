package book;

public class NullPointerException1 {
    public static void main(String[] args) {
        B obj = new C();
        obj.doSomething();

        String[][] oldLaptops = {{"Dell", "Toshiba", "Vaio"}, null, {"IBM"}, new String[10]};
        System.out.println(oldLaptops[0][0]);
        System.out.println(oldLaptops[1]);
        System.out.println(oldLaptops[3][6]);
        System.out.println(oldLaptops);
        //This is the only statement that throws a NullPointerException
        System.out.println(oldLaptops[3][0].length());
    }
    static class A {
        public int x = 1;
        static void doSomething() {
            System.out.println("method in A");
        }
    }

    static class B extends A {
        public static void doSomething() {
            System.out.println("method in B");
        }
    }

    static class C extends B {
        public static void doSomething() {
            System.out.println("method in C");
        }
    }
}
