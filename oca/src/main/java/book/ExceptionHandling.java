package book;

public class ExceptionHandling {
    public void main(String[] args) {
        MultipleReturn var = new MultipleReturn();
        System.out.println("In Main: " + var.getInt());
    }
    class MultipleReturn {
        int getInt() {
            int returnVal = 10;
            try {
                String[] students = {"Harry", "Paul"};
                System.out.println(students[5]);
            } catch (ArrayIndexOutOfBoundsException aioob){
                System.out.println("Incorrect array Index");
                return returnVal;
            } catch (Exception e) {
                System.out.println("About to return:" + returnVal);
                return returnVal;
            } finally {
                returnVal += 10;
                System.out.println("Return values is now:" + returnVal);
            }
            return returnVal;
        }
    }
}
