package StringSolver.Exceptions;

/**
 * Ошибка преобразования выражения
 * Например, не сбалансированы скобки или выражение пусто
 */
public class CalculatorConvertException extends CalculatorException {
    public CalculatorConvertException(String message) {
        super(message);
    }
}
