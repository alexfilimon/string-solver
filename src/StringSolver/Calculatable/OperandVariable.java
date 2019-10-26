package StringSolver.Calculatable;

import StringSolver.Library.Calculatable;
import StringSolver.Managers.VariableManager;

/**
 * Математический операнд арифметического выражения,
 * значение которого не указывается явно, но которое
 * будет установлено непосредственно во время вычисления
 * значения всего выражения
 */
public class OperandVariable implements Calculatable<Double> {

    private String name;

    public OperandVariable(String name) {
        this.name = name;
    }

    @Override
    public Double calculate() {
        return VariableManager.shared.getValue(name);
    }
}
