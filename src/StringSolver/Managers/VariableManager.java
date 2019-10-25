package StringSolver.Managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Менеджер переменных
 * Запрашивает и хранит значение каждой переменной,
 * встреченной в выражении
 */
public class VariableManager {

    public static final VariableManager shared = new VariableManager();

    private Map<String, Double> map = new HashMap();

    /**
     * Проверяет, может ли [variableName] быть переменной
     */
    public static boolean isVariable(String variableName) {
        Pattern p = Pattern.compile("^[_a-zA-Z]\\w*$");
        Matcher m = p.matcher(variableName);
        return m.matches();
    }

    public Double getValue(String name) {
        if (map.containsKey(name)) {
            return map.get(name);
        } else {
            Double value = readValue(name);
            while (value == null) {
                value = readValue(name);
            }
            map.put(name, value);
            return value;
        }
    }

    public void setVariables(HashMap<String, Double> newVariables) {
        map = newVariables;
    }

    public void clearVariables() {
        map.clear();
    }

    private Double readValue(String name) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n--- Input variable ---\n")
                .append(name)
                .append(": ");
        System.out.print(builder);

        try {
            Scanner sc = new Scanner(System.in);
            return Double.parseDouble(sc.next());
        } catch (Exception e) {
            return null;
        }
    }
}
