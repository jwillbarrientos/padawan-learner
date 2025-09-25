package enthuwareOcp;

interface Classic {
    int version = 1;
    public void read();
}

class MediaReader implements Classic {
    int version = 2;
    public void read() {
        System.out.println(version);
        //System.out.println((Classic)version);
        System.out.println(((Classic)this).version);
        //System.out.println(this.Classic.version);
        //System.out.println(MediaReader.version);
    }
}

public class ReaderTest {
    public static void main(String[] args) {
        MediaReader mr = new MediaReader();
        mr.read();
    }
}
