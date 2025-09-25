package enthuwareOcp;

class A {
    public A() {} // A1
    public A(String s) { // A2
        this();
        System.out.println("A :"+ s);
    }
}

class B extends A {
    public int B(String s) { // B1
        System.out.println("B :"+ s);
        return 0;
    }
}

class C extends B {
    private C() { // C1
        super();
    }
    public C(String s){ // C2
        this();
        System.out.println("C :"+ s);
    }
    public C(int i){} // C3
}
