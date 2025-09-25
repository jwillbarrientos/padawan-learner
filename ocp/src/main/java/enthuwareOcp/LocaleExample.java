package enthuwareOcp;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleExample {
    public static void main(String[] args) {
        Locale myloc = new Locale.Builder().setLanguage("en").setRegion("UK").build(); //L1
        ResourceBundle msgs = ResourceBundle.getBundle("book.mymsgs", myloc);
        Enumeration<String> en = msgs.getKeys();
        while(en.hasMoreElements()) {
            String key = en.nextElement();
            String val = msgs.getString(key);
            System.out.println(key+" : "+val);
        }
    }

    public void m1() {
        int i = 10;
        try {
            assert i == 20;
        } catch(Exception e) {
            i = 20;    
        }
        System.out.println(i);
    }
}
