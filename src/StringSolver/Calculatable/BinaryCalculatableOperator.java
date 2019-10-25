package StringSolver.Calculatable;

import StringSolver.Exceptions.CalculatorException;
import StringSolver.Library.Calculatable;
import StringSolver.Managers.OperatorManager;

/**
 * Обертка для [BinaryOperator], которая упрощает
 * получение значения оператора
 */
public class BinaryCalculatableOperator implements Calculatable<Double> {
    private String operator;
    private Calculatable<Double> operand1;
    private Calculatable<Double> operand2;

    public BinaryCalculatableOperator(String operator, Calculatable<Double> operand1, Calculatable<Double> operand2) {
        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    /**
     * Проверяет, является ли оператор [string] бинарным
     */
    public static boolean isBinarOperator(String string) {
        return OperatorManager.shared.isBinaryOperator(string);
    }

    @Override
    public Double calculate() throws CalculatorException {
        Double val1 = operand1.calculate();
        Double val2 = operand2.calculate();
        return OperatorManager.shared.calculateBinaryOperator(operator, val1, val2);
    }
}
