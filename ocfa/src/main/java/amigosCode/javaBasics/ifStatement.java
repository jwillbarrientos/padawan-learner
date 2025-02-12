package amigosCode.javaBasics;

public class ifStatement {
    public static void main(String[] args){
        int age = 16;
        if(age > 18){
            System.out.println("you are an adult");
        }else if((age >= 15) && age<=17){
            System.out.println("you are almost an adult");
        }else{
            System.out.println("you are still a kid");
        }
    }
}