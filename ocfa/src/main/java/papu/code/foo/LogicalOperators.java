package papu.code.foo;

public class LogicalOperators {
    public static void main(String[] args){
        boolean itsAnAdult = true;
        boolean itsAPapu = false;

        //and is "&&" and or is "||"
        System.out.println(itsAnAdult && itsAPapu);
        System.out.println(itsAnAdult || itsAPapu);

        //if you want say, it's not, you must type "!" before a variable
        System.out.println(itsAnAdult || !itsAPapu);

        //you can group logical operators, like this
        boolean itsMen = true;
        System.out.println((itsAnAdult || !itsAPapu) && itsMen);
    }
}