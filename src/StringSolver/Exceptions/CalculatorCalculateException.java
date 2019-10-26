package StringSolver.Exceptions;

/**
 * Ошибка вычисления значения операнда-переменной [OperandVariable]
 */
public class CalculatorCalculateException extends CalculatorException {
    public CalculatorCalculateException(String message) {
        super(message);
    }
}
