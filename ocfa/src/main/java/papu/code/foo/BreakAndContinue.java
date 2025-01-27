package papu.code.foo;

public class BreakAndContinue {
    public static void main(String[] args){
        String [] names = {"Ali", "Jona", "Alan", "Deivi"};

        //in this case, break the loop when found the name Alan, so only the names before of Alan will be printed
        for (String name : names) {
            if(name.equals("Alan")){
                break;
            }
            System.out.println(name);
        }

        //in this case, the loop will back to the beginning if the name start with A, so the names that start with A not wil not be printed
        for (String name : names) {
            if(name.startsWith("A")){
                continue;
            }
            System.out.println(name);
        }
    }
}