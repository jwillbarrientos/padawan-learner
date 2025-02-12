package simplyLearn.inheritance.exercises;

class TestClassxd {
        public static void main(String[] args) {
            double interest = FinancialFunctions.computeSimpleInterest(1000, 10, 1);
            System.out.println(interest);
        }
}
class FinancialFunctions {
    static double computeSimpleInterest(double p, double r, double t) {
        return p*r*t/100;
    }
}
