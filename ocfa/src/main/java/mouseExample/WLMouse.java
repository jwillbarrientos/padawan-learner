package mouseExample;

public class WLMouse extends GenericMouse {
    @Override
    public void click() {
        this.click(true);
        System.out.println("Click suave");
    }
    //Overload
    public void click(boolean fuerte) {
        if(fuerte) {
            super.click();
        }else{
            System.out.println("Click suave");
        }
    }
    
    public static class PulsarMouse implements Mouse {
        public void click() {
            System.out.println("Click purete!");
        }
    }

    public static void main(String[] args) {
        Mouse mouse1 = new PulsarMouse();
        mouse1.click();
        WLMouse mouse2 = new WLMouse();
        mouse2.click();
    }
}
