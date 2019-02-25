package StringSolver.Calculatable;

import StringSolver.Library.Calculatable;
import StringSolver.Managers.VariableManager;

public class OperandVariable implements Calculatable<Double> {
    private String name;

    public OperandVariable(String name) {
        this.name = name;
    }

    @Override
    public Double calculate() {
        return VariableManager.shared.getValue(name);
    }

}
