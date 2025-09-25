package enthuwareOcp;

import java.util.ArrayList;

class Book1 {}
class TextBook extends Book1 {}

public class BookList extends ArrayList<Book1> {
    public int count = 0;
    //public boolean add(Object o) {
    //    if(o instanceof Book1) return super.add((Book1) o);
    //    else return count++ == -1;
    //}
}
