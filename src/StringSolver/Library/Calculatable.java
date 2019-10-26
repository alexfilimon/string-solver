package StringSolver.Library;

import StringSolver.Exceptions.CalculatorException;

/**
 * Маркерный интерфейс, показывающий, что операнд
 * является вычисляемым и его значение будет типа
 */
public interface Calculatable<T> {
    T calculate() throws CalculatorException;
}
