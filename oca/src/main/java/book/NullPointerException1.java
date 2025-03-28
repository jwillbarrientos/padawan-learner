package book;

public class NullPointerException1 {
   public class TestClass{
       public static void main(String[] args) {
           try{
               hello();
           }
           catch(MyException me){
               System.out.println(me);
           }
       }

       static void hello() throws MyException{
           int[] dear = new int[7];
           dear[0] = 747;
           foo();
       }

       static void foo() throws MyException{
           throw new MyException("Exception from foo");
       }
   }

    static class MyException extends Exception {
        public MyException(String msg){
            super(msg);
        }
    }// public static void main(String[] args) {
    //     String[][] oldLaptops = {{"Dell", "Toshiba", "Vaio"}, null, {"IBM"}, new String[10]};
    //     System.out.println(oldLaptops[0][0]);
    //     System.out.println(oldLaptops[1]);
    //     System.out.println(oldLaptops[3][6]);
    //     System.out.println(oldLaptops);
    //     //This is the only statement that throws a NullPointerException
    //     System.out.println(oldLaptops[3][0].length());
    // }
}
