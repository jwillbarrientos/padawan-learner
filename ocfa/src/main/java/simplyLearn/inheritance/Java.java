package simplyLearn.inheritance;

public class Java extends Computers {
    int x = 7;
    void classroom() {
        System.out.println("Focus on java");
        System.out.println(super.x);
    }
    public static void main(String[] args) {
        Java p = new Java();
        p.enrolled();
        p.studies();
        p.classroom();
    }
}