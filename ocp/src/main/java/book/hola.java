package book;

class hola {}

class Algo {

    public static void main(String[] args) {
        new Algo().hacerAlgo();
    }

    private String prop1;

    public void hacerAlgo() {
        InnerClass inner = new InnerClass();
        inner.hacerOtraCosa();

        InnerImplicitStatic innerImplicitStatic = new InnerImplicitStatic();
        innerImplicitStatic.hacerOtraCosa();

    }

    class InnerClass {
        public void hacerOtraCosa() {
            prop1 = "asdf";
        }
    }
}

class InnerImplicitStatic {
    public void hacerOtraCosa() {
        //prop1 = "asdf"; //not compile
    }
}
