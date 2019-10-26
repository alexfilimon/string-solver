package StringSolver.Calculatable;

import StringSolver.Exceptions.CalculatorException;
import StringSolver.Library.Calculatable;
import StringSolver.Managers.OperatorManager;

/**
 * Обертка для [UnaryOperator], которая упрощает
 * получение значения оператора
 */
public class UnarCalculatableOperator implements Calculatable<Double> {

    private String operator;
    private Calculatable<Double> operand;

    public UnarCalculatableOperator(String operator, Calculatable<Double> operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public Double calculate() throws CalculatorException {
        Double val = operand.calculate();
        return OperatorManager.shared.calculateUnaryOperator(operator, val);
    }

    public static boolean isUnarOperator(String string) {
        return OperatorManager.shared.isUnaryOperator(string);
    }
}
