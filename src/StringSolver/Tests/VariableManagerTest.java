package StringSolver.Tests;

import StringSolver.Managers.VariableManager;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class VariableManagerTest {

    @Test
    public void isVariable() {
        // given
        List<String> goodVariablesNames = Arrays.asList(
            "goodVariable",
            "_goodVariable",
            "HelloWorld",
            "_123",
            "H123llo"
        );
        List<String> badVariablesNames = Arrays.asList(
                "123",
                "&hello",
                "hell$lo"
        );

        // when
        List<Boolean> gettedGoodVariables = goodVariablesNames.stream()
                .map(s -> VariableManager.isVariable(s))
                .collect(Collectors.toList());
        List<Boolean> gettedBadVariables = badVariablesNames.stream()
                .map(s -> VariableManager.isVariable(s))
                .collect(Collectors.toList());

        // then
        gettedGoodVariables.forEach(value -> assertEquals(true, value));
        gettedBadVariables.forEach(value -> assertEquals(false, value));
    }
}