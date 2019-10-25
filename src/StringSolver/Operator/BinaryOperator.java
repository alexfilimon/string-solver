package StringSolver.Operator;

import StringSolver.Library.Pair;

import java.util.function.Function;

/**
 * Бинарный оператор, тип значения которого [Double]
 */
public class BinaryOperator implements Priority {
    @Override
    public Integer getPriority() {
        return priority;
    }

    public Function<Pair<Double, Double>, Double> getFunction() {
        return closure;
    }

    private Integer priority;
    private Function<Pair<Double, Double>, Double> closure;

    public BinaryOperator(Integer priority, Function<Pair<Double, Double>, Double> closure) {
        this.priority = priority;
        this.closure = closure;
    }
}
