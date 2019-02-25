package StringSolver.Tests;

import org.junit.Assert;
import org.junit.Test;
import StringSolver.Managers.OperatorManager;

public class OperatorManagerTest {
    @Test
    public void testUnarySin() {
        // given
        Double expectedValue = 1.0;

        // when
        Double gettedValue = OperatorManager.shared.calculateUnaryOperator("sin", Math.PI / 2);

        // then
        Assert.assertEquals(expectedValue, gettedValue, 0.0001);
    }
    @Test
    public void testUnaryCos() {
        // given
        Double expectedValue = -1.0;

        // when
        Double gettedValue = OperatorManager.shared.calculateUnaryOperator("cos", Math.PI);

        // then
        Assert.assertEquals(expectedValue, gettedValue, 0.0001);
    }
    @Test
    public void testUnaryTan() {
        // given
        Double expectedValue = 1.0;

        // when
        Double gettedValue = OperatorManager.shared.calculateUnaryOperator("tan", Math.PI / 4);

        // then
        Assert.assertEquals(expectedValue, gettedValue, 0.0001);
    }
    @Test
    public void testBinaryAdd() {
        // given
        Double expectedValue = 5.0;

        // when
        Double gettedValue = OperatorManager.shared.calculateBinaryOperator("+", 2.0, 3.0);

        // then
        Assert.assertEquals(expectedValue, gettedValue, 0.0001);
    }
    @Test
    public void testBinarySub() {
        // given
        Double expectedValue = 1.0;

        // when
        Double gettedValue = OperatorManager.shared.calculateBinaryOperator("-", 3.0, 2.0);

        // then
        Assert.assertEquals(expectedValue, gettedValue, 0.0001);
    }
    @Test
    public void testBinaryMul() {
        // given
        Double expectedValue = 6.0;

        // when
        Double gettedValue = OperatorManager.shared.calculateBinaryOperator("*", 2.0, 3.0);

        // then
        Assert.assertEquals(expectedValue, gettedValue, 0.0001);
    }
    @Test
    public void testBinaryDiv() {
        // given
        Double expectedValue = 3.0;

        // when
        Double gettedValue = OperatorManager.shared.calculateBinaryOperator("/", 6.0, 2.0);

        // then
        Assert.assertEquals(expectedValue, gettedValue, 0.0001);
    }
}