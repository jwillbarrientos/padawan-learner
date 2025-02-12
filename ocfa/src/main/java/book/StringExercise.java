package book;

public class StringExercise {
    public static void main(String[] args) {
        String s = "a";
        String[] sa = {"a", s, s.substring(0, 1), new String("a"), "" + 'a'};
        for (int i = 0; i < sa.length; i++) {
            System.out.println(i);
            System.out.println(s == sa[i]);
            System.out.println(s.equals(sa[i]));
        }
    }
}
