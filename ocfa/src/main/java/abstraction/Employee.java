//package abstraction;
//
//public class Employee {
//
//    private int EmpId;
//
//    public Employee(String EmployeeName, String Gen, int EmployeeID) {
//        super(EmployeeName, Gen);
//        this.EmpId = EmployeeID;
//    }
//
//    @Override
//    public void Office() {
//        if(EmpId == 0) {
//            System.out.println("Employee logged out");
//        }else{
//            System.out.println("Employee logged in");
//        }
//    }
//
//    public static void main(String args[]) {
//        simplyLearn.inheritance.exercises.Person employee = new Employee("Pavithra", "Female", 1094826);
//
//        employee.Office();
//        employee.changeName("Pavithra Tripathy");
//        System.out.println(employee.toString());
//    }
//}