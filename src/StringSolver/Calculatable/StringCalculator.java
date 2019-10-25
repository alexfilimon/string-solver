package StringSolver.Calculatable;

import StringSolver.Exceptions.CalculatorCalculateException;
import StringSolver.Exceptions.CalculatorConvertException;
import StringSolver.Exceptions.CalculatorException;
import StringSolver.Library.Calculatable;
import StringSolver.Library.MutableInteger;
import StringSolver.Managers.OperatorManager;
import StringSolver.Managers.VariableManager;

import java.util.*;

import static StringSolver.Constants.EMPTY_STRING;
import static StringSolver.Constants.SPACE;

/**
 * Класс, вычисляющий значение арифметического выражения,
 * записанного в виде строки
 */
public class StringCalculator implements Calculatable<Double> {

    private String expression;

    public StringCalculator(String expression) {
        this.expression = expression;
    }

    public Double calculate() throws CalculatorException {
        // получение приоритетов операций
        Map<String, Integer> operators = OperatorManager.shared.getBinaryOperatorsWithPriority();
        operators.putAll(OperatorManager.shared.getUnaryOperatorsWithPriority());
        String converted = convertToRPN(expression, "(", ")", operators);

        // разбиение подготовленной строки
        String[] parts = converted.split("\\s+");

        // variable for calculating
        MutableInteger i = new MutableInteger(parts.length - 1);

        // построение дерева выражения
        Calculatable<Double> root = getCalculatable(parts, i);

        // вычисление значения выраженя
        return root.calculate();
    }

    private Calculatable<Double> getCalculatable(String[] parts, MutableInteger i) throws CalculatorException {
        String current = parts[i.intValue()];

        if (UnarCalculatableOperator.isUnarOperator(current)) {
            // унарный оператор
            i.decrement();
            return new UnarCalculatableOperator(current, getCalculatable(parts, i));
        } else if (BinaryCalculatableOperator.isBinarOperator(current)) {
            // бинарный оператор
            i.decrement();
            Calculatable<Double> operand1 = getCalculatable(parts, i);
            i.decrement();
            Calculatable<Double> operand2 = getCalculatable(parts, i);
            return new BinaryCalculatableOperator(current, operand2, operand1);
        } else if (isNum(current)) {
            // число
            return new Operand(current);
        } else if (VariableManager.isVariable(current)) {
            // переменная без значения
            return new OperandVariable(current);
        } else {
            throw new CalculatorCalculateException("Не распознан оператор или невалидное имя переменной: ".concat(current));
        }
    }

    private boolean isNum(String part) {
        try {
            Double.parseDouble(part);
        } catch (NumberFormatException error) {
            return false;
        }
        return true;
    }

    private String convertToRPN(
            String expression,
            String leftBracket,
            String rightBracket,
            Map<String, Integer> operations
    ) throws CalculatorConvertException {

        if (expression == null || expression.isBlank()) {
            throw new CalculatorConvertException("Выражение пусто");
        }

        List<String> result = new ArrayList<>();
        Stack<String> operationStack = new Stack<>();

        // Задание множества всех допустимых символов операции
        Set<String> operationsSymbols = new HashSet<>(Arrays.asList(OperatorManager.shared.getAllOperators()));
        operationsSymbols.add(leftBracket);
        operationsSymbols.add(rightBracket);

        expression = clearExpression(expression);

        int index = 0;
        boolean needFindNext = true;
        while (needFindNext) {
            int nextOperationIndex = expression.length();
            String nextOperation = EMPTY_STRING;
            // Поиск следующего оператора или скобки
            for (String operation : operationsSymbols) {
                int i = expression.indexOf(operation, index);
                if (i >= 0 && i < nextOperationIndex) {
                    nextOperation = operation;
                    nextOperationIndex = i;
                }
            }

            // Оператор не найден
            if (nextOperationIndex == expression.length()) {
                needFindNext = false;
            } else {
                // Если оператору или скобке предшествует операнд, добавляем его в выходную строку
                if (index != nextOperationIndex) {
                    result.add(expression.substring(index, nextOperationIndex));
                }
                // Обработка операторов и скобок
                // Открывающая скобка
                if (nextOperation.equals(leftBracket)) {
                    processLeftBracket(operationStack, nextOperation);
                }
                // Закрывающая скобка
                else if (nextOperation.equals(rightBracket)) {
                    processRightBracket(operationStack, result, leftBracket);
                }
                // Операция
                else {
                    processOperation(operationStack, result, leftBracket, operations, nextOperation);
                }
                index = nextOperationIndex + nextOperation.length();
            }
        }

        // Добавление в выходную строку операндов после последнего операнда
        if (index != expression.length()) {
            result.add(expression.substring(index));
        }
        // Пробразование выходного списка к выходной строке
        while (!operationStack.empty()) {
            result.add(operationStack.pop());
        }
        return result.stream()
                .reduce((a, b) -> a + SPACE + b)
                .orElse(EMPTY_STRING);
    }

    private String clearExpression(String expression) {
        return expression.replaceAll(SPACE, EMPTY_STRING);
    }

    private void processLeftBracket(Stack<String> operationStack, String nextOperation) {
        operationStack.push(nextOperation);
    }

    private void processRightBracket(
            Stack<String> operationStack,
            List<String> result,
            String leftBracket
    ) throws CalculatorConvertException {
        while (!operationStack.peek().equals(leftBracket)) {
            result.add(operationStack.pop());
            if (operationStack.empty()) {
                throw new CalculatorConvertException("Несбалансированные скобки!");
            }
        }
        operationStack.pop();
    }

    private void processOperation(
            Stack<String> operationStack,
            List<String> result,
            String leftBracket,
            Map<String, Integer> operations,
            String nextOperation
    ) {
        while (!operationStack.empty() && !operationStack.peek().equals(leftBracket) &&
                (operations.get(nextOperation) >= operations.get(operationStack.peek()))) {
            result.add(operationStack.pop());
        }
        operationStack.push(nextOperation);
    }
}
