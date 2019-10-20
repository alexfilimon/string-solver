package StringSolver.Operator;

import StringSolver.Library.Tuple;

import java.util.function.Function;

public class BinaryOperator implements PriorityGettable {
    @Override
    public Integer getPriority() {
        return priority;
    }

    public Function<Tuple<Double, Double>, Double> getFunction() {
        return closure;
    }

    private Integer priority;
    private Function<Tuple<Double, Double>, Double> closure;

    public BinaryOperator(Integer priority, Function<Tuple<Double, Double>, Double> closure) {
        this.priority = priority;
        this.closure = closure;
    }
}
