package StringSolver.Calculatable;

import StringSolver.Library.Calculatable;

/**
 * Операнд математического выражения
 */
public class Operand implements Calculatable<Double> {

    private Double number;

    public Operand(String stringNum) {
        try {
            number = Double.parseDouble(stringNum);
        } catch (NumberFormatException error) {
            throw error;
        }
    }

    @Override
    public Double calculate() {
        return number;
    }
}
