public class AccessModifiers {
    private int age;

    public void setAge(int yrs) {
        if(yrs < 0) throw new IllegalArgumentException();
        else this.age = yrs;
    }

    public int getAge(
            //AccessModifiers reference
    ) {
        int age = 1;
        return this.age;
      //return reference.age;
    }
}
