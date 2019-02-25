package StringSolver.Library;

import StringSolver.Exceptions.CalculatorException;

public interface Calculatable<T> {
    T calculate() throws CalculatorException;
}
