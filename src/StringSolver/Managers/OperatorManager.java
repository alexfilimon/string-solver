package StringSolver.Managers;

import StringSolver.Library.Pair;
import StringSolver.Operator.BinaryOperator;
import StringSolver.Operator.UnaryOperator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Менеджер операторов
 * Задает правила вычисления значения каждому оператору
 */
public class OperatorManager {

    public static final OperatorManager shared = new OperatorManager();
    private HashMap<String, BinaryOperator> binaryOperators;
    private HashMap<String, UnaryOperator> unaryOperators;

    public OperatorManager() {
        binaryOperators = new HashMap<>();
        binaryOperators.put("+", new BinaryOperator(3, (Pair<Double, Double> pair) -> pair.x + pair.y));
        binaryOperators.put("-", new BinaryOperator(3, (Pair<Double, Double> pair) -> pair.x - pair.y));
        binaryOperators.put("*", new BinaryOperator(2, (Pair<Double, Double> pair) -> pair.x * pair.y));
        binaryOperators.put("/", new BinaryOperator(2, (Pair<Double, Double> pair) -> pair.x / pair.y));

        unaryOperators = new HashMap<>();
        unaryOperators.put("sin", new UnaryOperator(1, Math::sin));
        unaryOperators.put("cos", new UnaryOperator(1, Math::cos));
        unaryOperators.put("tan", new UnaryOperator(1, Math::tan));
        unaryOperators.put("sqrt", new UnaryOperator(1, Math::sqrt));
    }

    public String[] getAllOperators() {
        return Stream.concat(
                Arrays.stream(getBinaryOperators()),
                Arrays.stream(getUnaryOperators())
        ).toArray(String[]::new);
    }

    public String[] getBinaryOperators() {
        int size = binaryOperators.size();
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
        int size = unaryOperators.size();
        return unaryOperators.keySet().toArray(new String[size]);
    }

    public Map<String, Integer> getUnaryOperatorsWithPriority() {
        return unaryOperators.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                e -> e.getKey(),
                                e -> e.getValue().getPriority()
                        )
                );
    }

    /**
     * Вычисляет значение бинарного оператора [operatorName]
     * между операндами [operand1] и [operand2]
     */
    public Double calculateBinaryOperator(String operatorName, Double operand1, Double operand2) {
        return binaryOperators.get(operatorName).getFunction().apply(new Pair<>(operand1, operand2));
    }

    /**
     * Вычисляет значение унарного оператора [operatorName]
     * для операнда [operand]
     */
    public Double calculateUnaryOperator(String operatorName, Double operand) {
        return unaryOperators.get(operatorName).getFunction().apply(operand);
    }

    /**
     * Проверяет, является ли [operatorName] унарным операндом
     */
    public boolean isUnaryOperator(String operatorName) {
        return unaryOperators.containsKey(operatorName);
    }

    /**
     * Проверяет, является ли [operatorName] бинарным операндом
     */
    public boolean isBinaryOperator(String operatorName) {
        return binaryOperators.containsKey(operatorName);
    }
}
