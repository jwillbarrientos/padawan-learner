package book;

public class TestClass {
    public static void main(String[] args) throws Exception {
        System.out.println(args.length);
        for(int i=0; i<args.length; i++) {
            System.out.println("args["+i+"] = \"" + args[i]+"\"");
        }
    }
}
