package book;

import java.util.ArrayList;

public class ClassCastException1 {
    public static void main(String[] args) {
        ArrayList<Ink> inks = new ArrayList<Ink>();
        inks.add(new Ink());
        inks.add(new ColorInk());
        inks.add(new BlackInk());
        BlackInk blackInk = (BlackInk) inks.get(2);
        Printable printable = (Printable) inks.get(1);
    }
}

class Ink {}
interface Printable {}
class ColorInk extends Ink implements Printable {}
class BlackInk extends Ink {}