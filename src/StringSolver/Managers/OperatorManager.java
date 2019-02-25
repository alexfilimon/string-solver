package StringSolver.Managers;

import StringSolver.Operator.BinaryOperator;
import StringSolver.Operator.UnaryOperator;
import StringSolver.Library.Tuple;
import org.codehaus.groovy.runtime.ArrayUtil;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OperatorManager {
    private HashMap<String, BinaryOperator> binaryOperators;
    private HashMap<String, UnaryOperator> unaryOperators;

    public OperatorManager() {
        binaryOperators = new HashMap();
        binaryOperators.put("+", new BinaryOperator(3, (Tuple<Double, Double> tuple) -> tuple.x + tuple.y));
        binaryOperators.put("-", new BinaryOperator(3, (Tuple<Double, Double> tuple) -> tuple.x - tuple.y));
        binaryOperators.put("*", new BinaryOperator(2, (Tuple<Double, Double> tuple) -> tuple.x * tuple.y));
        binaryOperators.put("/", new BinaryOperator(2, (Tuple<Double, Double> tuple) -> tuple.x / tuple.y));

        unaryOperators = new HashMap();
        unaryOperators.put("sin", new UnaryOperator(1, (param) -> Math.sin(param)));
        unaryOperators.put("cos", new UnaryOperator(1, (param) -> Math.cos(param)));
        unaryOperators.put("tan", new UnaryOperator(1, (param) -> Math.tan(param)));
        unaryOperators.put("sqrt", new UnaryOperator(1, (param) -> Math.sqrt(param)));
    }

    public static final OperatorManager shared = new OperatorManager();

    public String[] getAllOperators() {
        return Stream.concat(
                Arrays.stream(getBinaryOperators()),
                Arrays.stream(getUnaryOperators())
        ).toArray(String[]::new);
    }

    public String[] getBinaryOperators() {
        Integer size = binaryOperators.size();
        return binaryOperators.keySet().toArray(new String[size]);
    }

    public Map<String, Integer> getBinaryOperatorsWithPriority() {
        return binaryOperators.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().getPriority()
                ));
    }

    public String[] getUnaryOperators() {
        Integer size = unaryOperators.size();
        return unaryOperators.keySet().toArray(new String[size]);
    }

    public Map<String, Integer> getUnaryOperatorsWithPriority() {
        return unaryOperators.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().getPriority()
                ));
    }

    public Double calculateBinaryOperator(String operatorName, Double operand1, Double operand2) {
        return binaryOperators.get(operatorName).getFunction().apply(new Tuple<>(operand1, operand2));
    }

    public Double calculateUnaryOperator(String operatorName, Double operand) {
        return unaryOperators.get(operatorName).getFunction().apply(operand);
    }

    public boolean isUnaryOperator(String operatorName) {
        return unaryOperators.containsKey(operatorName);
    }

    public boolean isBinaryOperator(String operatorName) {
        return binaryOperators.containsKey(operatorName);
    }
}
