package StringSolver.Managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableManager {
    public static final VariableManager shared = new VariableManager();

    private Map<String, Double> map = new HashMap<String, Double>();

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

    public void setVariables(HashMap<String, Double> newVaraiables) {
        map = newVaraiables;
    }

    public void cleanVariables() {
        map = new HashMap<String, Double>();
    }

    private Double readValue(String name) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n--- Input variable ---\n").append(name).append(": ");
        String string = builder.toString();
        System.out.print(string);

        try {
            Scanner sc = new Scanner(System.in);
            Double d = Double.parseDouble(sc.next());
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isVariable(String variableName) {
        Pattern p = Pattern.compile("^[_a-zA-Z]\\w*$");
        Matcher m = p.matcher(variableName);
        return m.matches();
    }
}
