package enthuwareOcp;

import java.util.*;

public class TestClass {
    public <E extends CharSequence> Collection<E> getWordsStartingWith(Collection<E> input, char ch) {
        Collection<E> returnValue = new ArrayList<E>();
        int len = input.size();
        for (E e : input) {
            if (e.charAt(0) == ch)
                returnValue.add(e);
        }
        return returnValue;
    }

    public void checkIt() {
        List<String> a = new ArrayList<String>();
        a.add("apple");
        a.add("cherry");
        Set<StringBuffer> b = new HashSet<StringBuffer>();
        b.add(new StringBuffer("pineapple"));
        Collection<String> ac = getWordsStartingWith(a, 'a'); // LINE 1
        Collection<StringBuffer> bc = getWordsStartingWith(b, 'b');
    }
}
