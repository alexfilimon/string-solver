package StringSolver;

import StringSolver.Managers.OperatorManager;

public class Program {
    public static void main(String[] args) {
        // String polizString = "25 a - sin   35 2 / * 33 +";
        // String polizString = "a b + 32 / b +";
        String string = "2.3 + 1 * sin(c)";
        StringSolver.Calculatable.StringCalculator calculator = new StringSolver.Calculatable.StringCalculator(string);
        System.out.println("Значение: ");
        try {
            System.out.println(calculator.calculate());
        } catch (Exception e) {
            System.out.print("Невозможно вычислить выражение: ");
            System.out.println(e);
        }

    }
}
