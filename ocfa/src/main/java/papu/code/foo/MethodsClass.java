package papu.code.foo;

public class MethodsClass {
    public static void main(String[] args) {
        //there are methods that are default in java, like these
        /*
        System.out.println
        toUpperCase()
        toLowerCase()
        startsWith("")
        endsWith("")
         */
        //all of these are methods that java provides, but you can create your own methods, take for example this
        char[] letters = {'A', 'A', 'B', 'C', 'C', 'C'};
        int count = countOcurrences(letters, 'A');
        System.out.println(count);
    }
    public static int countOcurrences (char[] letters, char searchLetter) {
        int count = 0;
        for(char letter : letters) {
            if(letter == searchLetter) {
                count++;
            }
        }
        return count;
    }
}
