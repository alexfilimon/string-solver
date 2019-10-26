package StringSolver.Operator;

import java.util.function.Function;

/**
 * Унарный оператор, тип значения которого [Double]
 */
public class UnaryOperator implements Priority {

    private Integer priority;
    private Function<Double, Double> closure;

    public UnaryOperator(Integer priority, Function<Double, Double> closure) {
        this.priority = priority;
        this.closure = closure;
    }

    @Override
    public Integer getPriority() {
        return priority;
    }

    /**
     * Возвращает метод, вычисляющий значение унарного оператора
     */
    public Function<Double, Double> getFunction() {
        return closure;
    }
}
