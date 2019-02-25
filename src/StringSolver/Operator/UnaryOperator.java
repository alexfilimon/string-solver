package StringSolver.Operator;

import java.util.function.Function;

public class UnaryOperator implements PriorityGettable {
    @Override
    public Integer getPriority() {
        return priority;
    }

    public Function<Double, Double> getFunction() {
        return closure;
    }

    private Integer priority;
    private Function<Double, Double> closure;

    public UnaryOperator(Integer priority, Function<Double, Double> closure) {
        this.priority = priority;
        this.closure = closure;
    }
}
