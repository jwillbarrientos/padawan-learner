package papu.code.foo;

import java.sql.SQLOutput;

public class SwitchStatement {
    public static void main(String[] args) {
        String gender = "FEMALE";
        //switch(gender.toUpperCase()) {
        switch (gender) {
            case "FEMALE":
                System.out.println("you are a female");
                break;
            case "MALE":
                System.out.println("you are a male");
                break;
            case "PREFER_NOT_SAY":
                System.out.println("you prefer not say your gender");
                break;
            default:
                System.out.println("unknown gender");
        }
    }
}