package StringSolver.Calculatable;

import StringSolver.Exceptions.CalculatorCalculateException;
import StringSolver.Exceptions.CalculatorConvertException;
import StringSolver.Exceptions.CalculatorException;
import StringSolver.Library.Calculatable;
import StringSolver.Library.MutableInteger;
import StringSolver.Managers.OperatorManager;
import StringSolver.Managers.VariableManager;

import java.util.*;

public class StringCalculator implements Calculatable<Double> {

    private String string;

    public StringCalculator(String string) {
        this.string = string;
    }

    public Double calculate() throws CalculatorException {
        // get map of operators with priority
        Map<String, Integer> operators = OperatorManager.shared.getBinaryOperatorsWithPriority();
        operators.putAll(OperatorManager.shared.getUnaryOperatorsWithPriority());
        String converted = convertToRPN(string, "(", ")", operators);

        // parts of converted string
        String[] parts = converted.split("\\s+");

        // variable for calculating
        MutableInteger i = new MutableInteger(parts.length - 1);

        // build tree
        Calculatable<Double> root = getCalculatable(parts, i);

        // calculate
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
            throw new CalculatorCalculateException("not recognized operator or not valid variable name: ".concat(current));
        }
    }

    private boolean isNum(String part) {
        try {
            double d = Double.parseDouble(part);
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private String convertToRPN(String expression, String leftBracket, String rightBracket, Map<String, Integer> operations) throws CalculatorConvertException {
        if (expression == null || expression.length() == 0) {
            throw new CalculatorConvertException("Empty expression");
        }

        // output list
        List<String> out = new ArrayList<String>();

        // operations stack
        Stack<String> stack = new Stack<String>();

        // symbols of all operations
        Set<String> operationsSymbols = new HashSet<String>(Arrays.asList(OperatorManager.shared.getAllOperators()));
        operationsSymbols.add(leftBracket);
        operationsSymbols.add(rightBracket);

        // remove spaces
        expression = expression.replace(" ", "");

        int index = 0;
        boolean needFindNext = true;
        while(needFindNext) {
            int nextOperationIndex = expression.length();
            String nextOperation = "";
            // Поиск следующего оператора или скобки.
            for (String operation : operationsSymbols) {
                int i = expression.indexOf(operation, index);
                if (i >= 0 && i < nextOperationIndex) {
                    nextOperation = operation;
                    nextOperationIndex = i;
                }
            }

            // Оператор не найден.
            if (nextOperationIndex == expression.length()) {
                needFindNext = false;
            } else {
                // Если оператору или скобке предшествует операнд, добавляем его в выходную строку.
                if (index != nextOperationIndex) {
                    out.add(expression.substring(index, nextOperationIndex));
                }
                // Обработка операторов и скобок.
                // Открывающая скобка.
                if (nextOperation.equals(leftBracket)) {
                    stack.push(nextOperation);
                }
                // Закрывающая скобка.
                else if (nextOperation.equals(rightBracket)) {
                    while (!stack.peek().equals(leftBracket)) {
                        out.add(stack.pop());
                        if (stack.empty()) {
                            throw new CalculatorConvertException("Unmatched brackets");
                        }
                    }
                    stack.pop();
                }
                // Операция.
                else {
                    while (!stack.empty() && !stack.peek().equals(leftBracket) &&
                            (operations.get(nextOperation) >= operations.get(stack.peek()))) {
                        out.add(stack.pop());
                    }
                    stack.push(nextOperation);
                }
                index = nextOperationIndex + nextOperation.length();
            }
        }

        // Добавление в выходную строку операндов после последнего операнда.
        if (index != expression.length()) {
            out.add(expression.substring(index));
        }
        // Пробразование выходного списка к выходной строке.
        while (!stack.empty()) {
            out.add(stack.pop());
        }
        StringBuffer result = new StringBuffer();
        if (!out.isEmpty())
            result.append(out.remove(0));
        while (!out.isEmpty())
            result.append(" ").append(out.remove(0));

        return result.toString();
    }

}
