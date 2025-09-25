package enthuwareOcp;

public class MyProcessor {
    int value;
    public MyProcessor() {
        value = 10;
    }

    public MyProcessor(int value) {
        this.value = value;
    }

    public void process() {
        System.out.println("Processing "+value);
    }

    public static void main(String[] args) {

        //MyProcessor mp = MyProcessor::new;
        //mp.process();
    }
}
