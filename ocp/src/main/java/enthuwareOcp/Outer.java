package enthuwareOcp;

public class Outer {
    private double d = 10.0;

    class Inner {
        public void m1() {
            Outer.this.d = 20.0; //LINE 1
        }
    }
}
