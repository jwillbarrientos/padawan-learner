public class ParenthesesExcercise {
    public static void main(String[] args) {
        char[] array = {'(', '(', '(', ')', '(', ')', '(', ')', ')', '(', ')', ')'};
        System.out.println(isValidString(array));
    }

    public static boolean isValidString(char[] array) {
        if(array.length == 0 || array.length%2 != 0)
            return false;
        int counter = 0;
        for(int i = 0; i < array.length; i++) {
            if (array[i] == '(') {
                 counter++;
            } else {
                if(counter < 1)
                    return false;
                else
                    counter--;
            }
        }
        return counter == 0;
    }
}
