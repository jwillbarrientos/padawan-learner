package enthuwareOcp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingPreference {
    public static void main(String[] args) {
        String s1 = "a";
        String s2 = new String("a");
        System.out.println(s1.equals(s2));
        System.out.println(s1 == s2);
        String s3 = "b";
        List<String> list = new ArrayList<>();
        list.add(s1);
        list.add(s3);
        list.add(s2);
        Collections.sort(list);
        System.out.println(list.indexOf(s1));
        System.out.println(list.indexOf(s2));
        System.out.println(list.indexOf(s3));
        System.out.println(list);
    }
}
