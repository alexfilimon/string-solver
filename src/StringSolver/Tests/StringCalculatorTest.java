package StringSolver.Tests;

import StringSolver.Calculatable.StringCalculator;
import StringSolver.Managers.VariableManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StringCalculatorTest {

    private Double accuracy = 0.001;

    @Before
    public void setupVariableManager() {
        VariableManager.shared.cleanVariables();
    }

    @Test
    public void testThatCalculateProperlyCase_1() {
        // given
        String expression = "(1 - 4.141592583565) + cos(c) + c";
        Double expected = -1.0;
        HashMap<String, Double> variables = new HashMap<>();
        variables.put("c", 3.141592583565);

        // then
        test(expression, variables, expected);
    }

    @Test
    public void testThatCalculateProperlyCase_2() {
        // given
        String expression = "(25 - 5) / (cos(2*pi) * 2) + a";
        Double expected = 30.0;
        HashMap<String, Double> variables = new HashMap<>();
        variables.put("pi", 3.141592583565);
        variables.put("a", 20.0);

        // then
        test(expression, variables, expected);
    }

    @Test
    public void testThatCalculateProperlyCase_3() {
        // given
        String expression = "a + a + b + b + c * (tan(pi / 4))";
        Double expected = 39.0;
        HashMap<String, Double> variables = new HashMap<>();
        variables.put("pi", 3.141592583565);
        variables.put("a", 2.0);
        variables.put("b", 10.0);
        variables.put("c", 15.0);

        // then
        test(expression, variables, expected);
    }

    @Test
    public void testThatCalculateProperlyCase_4() {
        // given
        String expression = "(tan(sin(pi)) + 2) + (a - b) * (b)";
        Double expected = 22.0;
        HashMap<String, Double> variables = new HashMap<>();
        variables.put("pi", 3.141592583565);
        variables.put("a", 12.0);
        variables.put("b", 10.0);

        // then
        test(expression, variables, expected);
    }

    @Test
    public void testThatCalculateProperlyCase_5() {
        // given
        String expression = "x + y + sqrt(x)";
        Double expected = 30.0;
        HashMap<String, Double> variables = new HashMap<>();
        variables.put("x", 16.0);
        variables.put("y", 10.0);

        // then
        test(expression, variables, expected);
    }

    @Test
    public void testThatCalculateProperlyCase_6() {
        // given
        String expression = "x - x + x";
        Double expected = 30.0;
        HashMap<String, Double> variables = new HashMap<>();
        variables.put("x", 30.0);

        // then
        test(expression, variables, expected);
    }

    @Test
    public void testThatCalculateFailCase_1() {
        // given bas brackets
        String expression = "x + y + sqrt((x + y)";

        testFail(expression);
    }

    @Test
    public void testThatCalculateFailCase_2() {
        // given: bad variable
        String expression = "1x + y + sqrt(x + y)";

        testFail(expression);
    }

    private void test(String expression, HashMap<String, Double> variables, Double expected) {
        VariableManager.shared.setVariables(variables);
        StringCalculator calculator = new StringCalculator(expression);

        // when
        Double calculated = null;
        try {
            calculated = calculator.calculate();
        } catch (Exception e) {
            assertTrue(false);
        }

        // then
        assertEquals(calculated, expected, accuracy);
    }

    private void testFail(String expression) {
        StringCalculator calculator = new StringCalculator(expression);

        // then
        Double calculated = null;
        try {
            calculated = calculator.calculate();
            assertTrue("Expected Exception, but it was not", false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}